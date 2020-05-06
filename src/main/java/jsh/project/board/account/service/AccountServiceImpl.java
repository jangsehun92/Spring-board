package jsh.project.board.account.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jsh.project.board.account.Enum.AuthOption;
import jsh.project.board.account.dao.AccountDao;
import jsh.project.board.account.dao.AuthDao;
import jsh.project.board.account.dto.Account;
import jsh.project.board.account.dto.AccountAuthRequestDto;
import jsh.project.board.account.dto.AccountCreateDto;
import jsh.project.board.account.dto.AccountFindRequestDto;
import jsh.project.board.account.dto.AccountFindResponseDto;
import jsh.project.board.account.dto.AccountInfoResponseDto;
import jsh.project.board.account.dto.AccountPasswordDto;
import jsh.project.board.account.dto.AccountPasswordResetDto;
import jsh.project.board.account.dto.AccountPasswordResetRequestDto;
import jsh.project.board.account.dto.AccountResponseDto;
import jsh.project.board.account.dto.AuthDto;
import jsh.project.board.account.exception.AccountNotEmailChecked;
import jsh.project.board.account.exception.AccountNotFoundException;
import jsh.project.board.account.exception.BadAuthRequestException;
import jsh.project.board.account.exception.EmailAlreadyUsedException;
import jsh.project.board.account.exception.FindAccountBadRequestException;
import jsh.project.board.account.exception.PasswordNotMatch;
import jsh.project.board.article.dto.Article;
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
	
	@Override
	@Transactional
	public void register(AccountCreateDto dto) throws Exception {
		//DB에 저장하기 전에 권한 설정 과 비밀번호 암호화 처리
		log.info(dto.toString());
		dto.setPassword(passwordEncoder.encode(dto.getPassword()));
		dto.setRole("ROLE_USER");
		
		//계정 정보 저장
		accountDao.save(dto);
		//이메일인증관련 테이블에 이메일과 인증키 저장
		AuthDto authDto = createAuth(dto.getEmail(), AuthOption.SIGNUP);
		//인증 이메일 발송
		sendEmail(authDto);
	}
	
	@Override
	public AccountInfoResponseDto accountInfo(int id) {
		AccountResponseDto accountResponseDto = accountDao.findById(id);
		List<Article> articles = null;
		AccountInfoResponseDto dto = new AccountInfoResponseDto(accountResponseDto, articles);
		return dto;
	}
	
	@Override
	public void accountEdit(Account dto) {
		accountDao.edit(dto);
	}
	
	@Override
	public void passwordChange(Account account, AccountPasswordDto dto) {
		if(!passwordEncoder.matches(dto.getBeforePassword(), account.getPassword())) {
			throw new PasswordNotMatch();
		}
		//비밀번호 업데이트
		account.setPassword(passwordEncoder.encode(dto.getAfterPassword()));
		accountDao.updatePassword(account);
	}
	
	//로그인 실패(비밀번호 틀림) 횟수 가져오기
	@Override
	public int accountFailureCount(String email) {
		return accountDao.failureCount(email);
	}
	
	//로그인 실패, 성공에 따른 failure_count 증가 및 초기화
	@Override
	public void updateFailureCount(String email, int failureCount) {
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("email", email);
		paramMap.put("failureCount", failureCount);
		accountDao.updateFailureCount(paramMap);
	}
	
	@Override
	public void updateLoginDate(String email) {
		accountDao.updateLoginDate(email);
	}
	
	//계정 잠금 및 해제
	@Override
	public void updateLocked(String email, int locked) {
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("email", email);
		paramMap.put("locked", locked);
		accountDao.updateLocked(paramMap);
	}
	
	//회원가입 시 이메일 중복 체크
	@Override
	public void emailCheck(String email) {
		if(accountDao.findEmail(email) != 0) {
			throw new EmailAlreadyUsedException();
		}
	}
	
	//계정 찾기 (처음 두글자 마지막 두글째 빼고 *처리하기)
	@Override
	public List<AccountFindResponseDto> findAccount(AccountFindRequestDto dto) throws AccountNotFoundException {
		List<AccountFindResponseDto> list = accountDao.findAccount(dto);
		if(list.isEmpty()) {
			throw new AccountNotFoundException();
		}
		return list;
	}
	
	//인증 이메일 재발송
	@Override
	public void resendEmail(String email) throws Exception {
		AuthDto authDto = updateAuth(email);
		sendEmail(authDto);
	}
	
	@Override
	public void sendResetEmail(AccountPasswordResetRequestDto dto) throws Exception {
		Account account = accountDao.findByEmail(dto.getEmail());
		if(account==null) {
			throw new AccountNotFoundException();
		}
		
		if(!account.findAccountCheck(dto)) {
			throw new FindAccountBadRequestException();
		}
		
		if(!account.isEnabled()) {
			throw new AccountNotEmailChecked();
		}
		AuthDto authDto = createAuth(dto.getEmail(), AuthOption.RESET);
		sendEmail(authDto);
	}
	
	//비밀번호 재설정(초기화)
	@Transactional
	@Override
	public void resetPassword(AccountPasswordResetDto dto) {
		System.out.println(dto.toString());
		AuthDto authDto = authDao.findByEmail(dto.getEmail());
		if(authDto == null || authDto.isAuthExpired() || !dto.getAuthKey().equals(authDto.getAuthKey()) || !dto.getAuthOption().equals(AuthOption.RESET.getOption())) {
			throw new BadAuthRequestException();
		}
		Account account = accountDao.findByEmail(dto.getEmail());
		if(account == null) {
			throw new AccountNotFoundException();
		}
		account.setPassword(passwordEncoder.encode(dto.getPassword()));
		accountDao.updatePassword(account);
		authDao.authKeyExpired(dto.toAuthDto());
	}
	
	//이메일 인증 링크를 통해 인증키 비교 후 상태값 변경 
	@Transactional
	@Override
	public void signUpConfirm(AccountAuthRequestDto dto) {
		log.info("accountService.resetPasswordConfirm(AccountAuthRequestDto dto) : "+dto.toString());
		AuthDto authDto = authDao.findByEmail(dto.getEmail());
		
		if(authDto == null || authDto.isAuthExpired() || !dto.getAuthKey().equals(authDto.getAuthKey()) || !dto.getAuthOption().equals(AuthOption.SIGNUP.getOption())) {
			throw new BadAuthRequestException();
		}
		accountDao.activetion(dto.getEmail());
		authDao.authKeyExpired(dto);
	}
	
	// 비밀번호 재설정을 위한 이메일을 통한 인증
	@Override
	public void resetPasswordConfirm(AccountAuthRequestDto dto) {
		log.info("accountService.resetPasswordConfirm(AccountAuthRequestDto dto) : "+dto.toString());
		AuthDto authDto = authDao.findByEmail(dto.getEmail());
		
		if (authDto == null || authDto.isAuthExpired() || !dto.getAuthKey().equals(authDto.getAuthKey()) || !dto.getAuthOption().equals(AuthOption.RESET.getOption())) {
			throw new BadAuthRequestException();
		}
	}

	@Transactional
	private AuthDto createAuth(String email, AuthOption authOption) {
		//인증 객채를 생성하기 전 체크하기(해당 요청에 맞는 인증객체가 있는지)
		Map<String, String> paramMap = new HashMap<>();
		paramMap.put("email", email);
		paramMap.put("authOption", authOption.getOption());
		
		AuthDto authDto = null;
		
		if(authDao.authCheck(paramMap) == 0) {
			String authKey = new AuthKey().getKey();
			authDto = new AuthDto(email, authKey, authOption.getOption());
			authDao.authSave(authDto);
		}else {
			authDto = updateAuth(email);
		}
		return authDto;
	}
	
	@Transactional
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
	
	private void sendEmail(AuthDto authDto) throws Exception {
		emailService.sendEmail(authDto);
	}


	

	

	

}
