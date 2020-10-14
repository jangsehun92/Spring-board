package jsh.project.board.account.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jsh.project.board.account.dao.AccountDao;
import jsh.project.board.account.domain.Account;
import jsh.project.board.account.dto.AuthDto;
import jsh.project.board.account.dto.request.RequestAccountCreateDto;
import jsh.project.board.account.dto.request.RequestAccountResetDto;
import jsh.project.board.account.dto.request.RequestEmailConfirmDto;
import jsh.project.board.account.dto.request.RequestEmailDto;
import jsh.project.board.account.dto.request.RequestFindAccountDto;
import jsh.project.board.account.dto.request.RequestPasswordDto;
import jsh.project.board.account.dto.request.RequestPasswordResetDto;
import jsh.project.board.account.dto.response.ResponseAccountInfoDto;
import jsh.project.board.account.dto.response.ResponseFindAccountDto;
import jsh.project.board.account.enums.AuthOption;
import jsh.project.board.account.enums.Role;
import jsh.project.board.account.exception.AccountNotEmailChecked;
import jsh.project.board.account.exception.AccountNotFoundException;
import jsh.project.board.account.exception.BadAuthRequestException;
import jsh.project.board.account.exception.EmailAlreadyUsedException;
import jsh.project.board.account.exception.FindAccountBadRequestException;
import jsh.project.board.account.exception.NotFoundAccountInfoException;
import jsh.project.board.account.exception.PasswordNotMatchException;
import jsh.project.board.global.infra.email.EmailService;

@Service
public class AccountServiceImpl implements AccountService{
	private static final Logger log = LoggerFactory.getLogger(AccountServiceImpl.class);
	
	private final AccountDao accountDao;
	private final PasswordEncoder passwordEncoder;
	private final AuthService authService;
	private final EmailService emailService;
	
	public AccountServiceImpl(AccountDao accountDao, AuthService authService, PasswordEncoder passwordEncoder, EmailService emailService) {
		this.accountDao = accountDao;
		this.authService = authService;
		this.passwordEncoder = passwordEncoder;
		this.emailService = emailService;
	}
	
	// 회원가입
	@Transactional
	@Override
	public void register(RequestAccountCreateDto dto) throws Exception {
		dto.checkPassword();
		dto.setPassword(passwordEncoder.encode(dto.getPassword()));
		dto.setRole(Role.USER);
		log.info(dto.toString());
		Account account = dto.toAccount();
		log.info(account.toString());
		accountDao.insertAccount(account);
		
		AuthDto authDto = authService.createAuth(account.getUsername(), AuthOption.SIGNUP);
		emailService.sendEmail(authDto);
	}
	
	// 계정 정보 찾기
	@Override
	public ResponseAccountInfoDto getAccountInfo(int id) {
		ResponseAccountInfoDto responseAccountInfoDto = accountDao.selectAccountInfo(id);
		if(responseAccountInfoDto == null) {
			throw new NotFoundAccountInfoException();
		}
		return responseAccountInfoDto;
	}
	
	// 회원정보 수정
	@Transactional
	@Override
	public void editAccount(Account account) {
		log.info(account.toString());
		accountDao.updateAccount(account);
	}
	
	// 비밀번호 변경
	@Transactional
	@Override
	public void changePassword(Account account, RequestPasswordDto dto) {
		log.info(account.toString());
		log.info(dto.toString());
		dto.checkPassword();
		if(!passwordEncoder.matches(dto.getBeforePassword(), account.getPassword())) {
			throw new PasswordNotMatchException();
		}
		account.changeAccountPassword(passwordEncoder.encode(dto.getAfterPassword()));
		accountDao.updatePassword(account);
	}
	
	// 로그인 실패(비밀번호 틀림) 횟수 가져오기
	@Override
	public int getAccountFailureCount(String email) {
		log.info(email);
		return accountDao.selectFailureCount(email);
	}
	
	// 로그인 실패, 성공에 따른 로그인 실패 횟수 증가 및 초기화
	@Override
	public void updateFailureCount(String email, int failureCount) {
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("email", email);
		paramMap.put("failureCount", failureCount);
		accountDao.updateFailureCount(paramMap);
	}
	
	// 로그인 성공시 마지막 로그인 날짜 업데이트
	@Transactional
	@Override
	public void updateLoginDate(String email) {
		log.info(email);
		accountDao.updateLoginDate(email);
	}
	
	// 계정 잠금 및 해제
	@Transactional
	@Override
	public void updateLocked(String email, int locked) {
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("email", email);
		paramMap.put("locked", locked);
		accountDao.updateLocked(paramMap);
	}
	
	// 회원가입 시 이메일 중복 체크
	@Override
	public void emailCheck(RequestEmailDto dto) {
		log.info(dto.toString());
		if(accountDao.selectEmailCount(dto) != 0) {
			throw new EmailAlreadyUsedException();
		}
	}
	
	// 가입한 계정 찾기
	@Override
	public List<ResponseFindAccountDto> getAccounts(RequestFindAccountDto dto) throws AccountNotFoundException {
		List<ResponseFindAccountDto> accountList = accountDao.selectAccounts(dto);
		if(accountList.isEmpty()) throw new AccountNotFoundException();
		for(ResponseFindAccountDto responseFindAccountDto : accountList) {
			log.info(responseFindAccountDto.toString());
		}
		return accountList;
	}
	
	// 인증 이메일 재발송
	@Transactional
	@Override
	public void resendEmail(RequestEmailDto dto) throws Exception {
		log.info(dto.toString());
		AuthDto authDto = authService.updateAuthKey(dto.getEmail());
		emailService.sendEmail(authDto);
	}
	
	// 비밀번호 재설정 인증이메일 발송
	@Transactional
	@Override
	public void sendResetEmail(RequestAccountResetDto dto) throws Exception {
		log.info(dto.toString());
		Account account = accountDao.selectAccount(dto.getEmail());
		
		if(account==null) { 
			throw new AccountNotFoundException(); 
		}
		if(!account.check(dto)) { 
			throw new FindAccountBadRequestException(); 
		}
		if(!account.isEnabled()) { 
			throw new AccountNotEmailChecked(); 
		}
		
		updateLocked(dto.getEmail(), 1); //계정 잠굼
		AuthDto authDto = authService.createAuth(dto.getEmail(), AuthOption.RESET); //인증키 생성
		emailService.sendEmail(authDto); //이메일 발송
	}
	
	// 비밀번호 재설정(초기화)
	@Transactional
	@Override
	public void resetPassword(RequestPasswordResetDto dto) {
		log.info(dto.toString());
		dto.checkPassword();
		Account account = accountDao.selectAccount(dto.getEmail());
		
		if(account == null) { 
			throw new AccountNotFoundException(); 
		}
		if(!authService.checkAuth(dto.toAuthCheckMap())) {
			throw new BadAuthRequestException(); 
		}
		
		account.changeAccountPassword(passwordEncoder.encode(dto.getPassword()));
		accountDao.updatePassword(account);
	}
	
	// 이메일 인증
	@Transactional
	@Override
	public void authConfirm(RequestEmailConfirmDto dto) {
		log.info(dto.toString());
		AuthDto authDto = authService.getAuth(dto.getEmail());
		
		if (authDto == null || !dto.getAuthKey().equals(authDto.getAuthKey()) || !dto.getAuthOption().equals(authDto.getAuthOption())) {
			throw new BadAuthRequestException();
		}
		authService.expired(dto);
	}
	
	// 계정 활성화
	@Override
	public void activation(String email) {
		log.info(email);
		accountDao.updateEnabled(email);
	}
	
}
