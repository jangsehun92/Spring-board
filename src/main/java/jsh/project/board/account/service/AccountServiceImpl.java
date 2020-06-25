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
import jsh.project.board.account.dao.AuthDao;
import jsh.project.board.account.domain.Account;
import jsh.project.board.account.dto.AuthDto;
import jsh.project.board.account.dto.request.RequestEmailConfirmDto;
import jsh.project.board.account.dto.request.RequestAccountCreateDto;
import jsh.project.board.account.dto.request.RequestEmailDto;
import jsh.project.board.account.dto.request.RequestFindAccountDto;
import jsh.project.board.account.dto.request.RequestPasswordDto;
import jsh.project.board.account.dto.request.RequestPasswordResetDto;
import jsh.project.board.account.dto.request.RequestAccountResetDto;
import jsh.project.board.account.dto.response.ResponseFindAccountDto;
import jsh.project.board.account.dto.response.ResponseAccountDto;
import jsh.project.board.account.enums.AuthOption;
import jsh.project.board.account.enums.Role;
import jsh.project.board.account.exception.AccountNotEmailChecked;
import jsh.project.board.account.exception.AccountNotFoundException;
import jsh.project.board.account.exception.BadAuthRequestException;
import jsh.project.board.account.exception.EmailAlreadyUsedException;
import jsh.project.board.account.exception.FindAccountBadRequestException;
import jsh.project.board.account.exception.PasswordNotMatchException;
import jsh.project.board.global.infra.email.EmailService;
import jsh.project.board.global.infra.util.AuthKey;

@Service
public class AccountServiceImpl implements AccountService{
	private static final Logger log = LoggerFactory.getLogger(AccountServiceImpl.class);
	
	private AccountDao accountDao;
	private AuthDao authDao;
	private PasswordEncoder passwordEncoder;
	private EmailService emailService;
	
	public AccountServiceImpl(AccountDao accountDao, AuthDao authDao, PasswordEncoder passwordEncoder, EmailService emailService) {
		this.accountDao = accountDao;
		this.authDao = authDao;
		this.passwordEncoder = passwordEncoder;
		this.emailService = emailService;
	}
	
	//회원가입
	@Transactional
	@Override
	public void register(RequestAccountCreateDto dto) throws Exception {
		dto.checkPassword();
		dto.setPassword(passwordEncoder.encode(dto.getPassword()));
		dto.setRole(Role.USER);
		accountDao.save(dto);
		//인증 테이블에 이메일과 인증키 저장
		AuthDto authDto = createAuth(dto.getEmail(), AuthOption.SIGNUP);
		sendEmail(authDto);
	}
	
	//계정 정보 찾기
	@Override
	public ResponseAccountDto accountInfo(int id) {
		ResponseAccountDto accountResponseDto = accountDao.findById(id);
		return accountResponseDto;
	}
	
	//회원정보 수정
	@Transactional
	@Override
	public void accountEdit(Account account) {
		accountDao.edit(account);
	}
	
	//비밀번호 변경
	@Transactional
	@Override
	public void passwordChange(Account account, RequestPasswordDto dto) {
		dto.checkPassword();
		if(!passwordEncoder.matches(dto.getBeforePassword(), account.getPassword())) {
			throw new PasswordNotMatchException();
		}
		account.setPassword(passwordEncoder.encode(dto.getAfterPassword()));
		accountDao.updatePassword(account);
	}
	
	// 로그인 실패(비밀번호 틀림) 횟수 가져오기
	@Override
	public int accountFailureCount(String email) {
		return accountDao.failureCount(email);
	}
	
	// 로그인 실패, 성공에 따른 failure_count 증가 및 초기화
	@Override
	public void updateFailureCount(String email, int failureCount) {
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("email", email);
		paramMap.put("failureCount", failureCount);
		accountDao.updateFailureCount(paramMap);
	}
	
	// 로그인 성공시 마지막로그인날짜 업데이트
	@Transactional
	@Override
	public void updateLoginDate(String email) {
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
		if(accountDao.findEmail(dto) != 0) {
			throw new EmailAlreadyUsedException();
		}
	}
	
	// 가입한 계정 찾기
	@Override
	public List<ResponseFindAccountDto> findAccount(RequestFindAccountDto dto) throws AccountNotFoundException {
		List<ResponseFindAccountDto> list = accountDao.findAccount(dto);
		if(list.isEmpty()) {
			throw new AccountNotFoundException();
		}
		return list;
	}
	
	// 인증 이메일 재발송
	@Transactional
	@Override
	public void resendEmail(RequestEmailDto dto) throws Exception {
		AuthDto authDto = updateAuth(dto.getEmail());
		sendEmail(authDto);
	}
	
	// 비밀번호 재설정 인증이메일 발송
	@Transactional
	@Override
	public void sendResetEmail(RequestAccountResetDto dto) throws Exception {
		Account account = accountDao.findByEmail(dto.getEmail());
		
		if(account==null) { throw new AccountNotFoundException(); }
		if(!account.findAccountCheck(dto)) { throw new FindAccountBadRequestException(); }
		if(!account.isEnabled()) { throw new AccountNotEmailChecked(); }
		
		updateLocked(dto.getEmail(), 1);
		AuthDto authDto = createAuth(dto.getEmail(), AuthOption.RESET);
		sendEmail(authDto);
	}
	
	// 비밀번호 재설정(초기화)
	@Transactional
	@Override
	public void resetPassword(RequestPasswordResetDto dto) {
		dto.checkPassword();
		Account account = accountDao.findByEmail(dto.getEmail());
		AuthDto authDto = authDao.findByEmail(dto.getEmail());
		
		if(account == null) {
			throw new AccountNotFoundException();
		}
		if(authDto == null || authDto.isAuthExpired() || !dto.getAuthKey().equals(authDto.getAuthKey()) || !dto.getAuthOption().equals(authDto.getAuthOption())) {
			throw new BadAuthRequestException();
		}
		
		account.setPassword(passwordEncoder.encode(dto.getPassword()));
		accountDao.updatePassword(account);
		authDao.authKeyExpired(dto.toAuthDto());
	}
	
	//회원가입 이메일 인증
	@Transactional
	@Override
	public void signUpConfirm(RequestEmailConfirmDto dto) {
		log.info("accountService.resetPasswordConfirm(AccountAuthRequestDto dto) : "+dto.toString());
		AuthDto authDto = authDao.findByEmail(dto.getEmail());
		
		if(authDto == null || authDto.isAuthExpired() || !dto.getAuthKey().equals(authDto.getAuthKey()) || !dto.getAuthOption().equals(AuthOption.SIGNUP.getValue())) {
			throw new BadAuthRequestException();
		}
		accountDao.activetion(dto.getEmail());
		authDao.authKeyExpired(dto);
	}
	
	//비밀번호 초기화 이메일 인증
	@Override
	public void resetPasswordConfirm(RequestEmailConfirmDto dto) {
		log.info("accountService.resetPasswordConfirm(AccountAuthRequestDto dto) : "+dto.toString());
		AuthDto authDto = authDao.findByEmail(dto.getEmail());
		
		if (authDto == null || authDto.isAuthExpired() || !dto.getAuthKey().equals(authDto.getAuthKey()) || !dto.getAuthOption().equals(AuthOption.RESET.getValue())) {
			throw new BadAuthRequestException();
		}
	}
	
	//인증키 생성
	private AuthDto createAuth(String email, AuthOption authOption) {
		Map<String, String> paramMap = new HashMap<>();
		paramMap.put("email", email);
		paramMap.put("authOption", authOption.getValue());
		
		AuthDto authDto = null;
		
		if(authDao.authCheck(paramMap) == 0) {
			String authKey = new AuthKey().getKey();
			authDto = new AuthDto(email, authKey, authOption.getValue());
			authDao.authSave(authDto);
		}else {
			authDto = updateAuth(email);
		}
		return authDto;
	}
	
	//인증키 업데이트
	private AuthDto updateAuth(String email) {
		AuthDto authDto = authDao.findByEmail(email);
		if(authDto == null || authDto.isAuthExpired()) {
			throw new BadAuthRequestException();
		}
		String authKey = new AuthKey().getKey();
		authDto.setAuthKey(authKey);
		authDao.updateAuthKey(authDto);
		return authDto;
	}
	
	//이메일 발송
	private void sendEmail(AuthDto authDto) throws Exception {
		emailService.sendEmail(authDto);
	}

}
