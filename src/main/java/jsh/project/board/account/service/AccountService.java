package jsh.project.board.account.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.security.auth.login.AccountNotFoundException;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jsh.project.board.account.Enum.AuthOption;
import jsh.project.board.account.dao.AccountDao;
import jsh.project.board.account.dto.Account;
import jsh.project.board.account.dto.AccountAuthRequestDto;
import jsh.project.board.account.dto.AccountCreateDto;
import jsh.project.board.account.dto.AccountFindRequestDto;
import jsh.project.board.account.dto.AccountFindResponseDto;
import jsh.project.board.account.dto.AccountPasswordResetDto;
import jsh.project.board.account.dto.AccountPasswordResetRequestDto;
import jsh.project.board.account.dto.AuthDto;
import jsh.project.board.account.exception.BadAuthRequestException;
import jsh.project.board.account.exception.EmailAlreadyUsedException;
import jsh.project.board.global.infra.email.EmailService;
import jsh.project.board.global.infra.util.AuthKey;

@Service
public class AccountService{
	
	private AccountDao accountDao;
	private PasswordEncoder passwordEncoder;
	private EmailService emailService;
	
	public AccountService(AccountDao accountDao, PasswordEncoder passwordEncoder, EmailService emailService) {
		this.accountDao = accountDao;
		this.passwordEncoder = passwordEncoder;
		this.emailService = emailService;
	}
	
	@Transactional
	public void register(AccountCreateDto dto) throws Exception {
		//DB에 저장하기 전에 권한 설정 과 비밀번호 암호화 처리
		dto.setPassword(passwordEncoder.encode(dto.getPassword()));
		dto.setRole("ROLE_USER");
		
		//계정 정보 저장
		accountDao.save(dto);
		//이메일인증관련 테이블에 이메일과 인증키 저장
		AuthDto authDto = createAuth(dto.getEmail(), AuthOption.SIGNUP);
		//인증 이메일 발송
		sendEmail(authDto);
	}
	
	//로그인 실패(비밀번호 틀림) 횟수 가져오기
	public int accountFailureCount(String email) {
		return accountDao.failureCount(email);
	}
	
	//로그인 실패, 성공에 따른 failure_count 증가 및 초기화
	public void updateFailureCount(String email, int failureCount) {
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("email", email);
		paramMap.put("failureCount", failureCount);
		accountDao.updateFailureCount(paramMap);
	}
	
	//계정 잠금 및 해제
	public void updateLocked(String email, int locked) {
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("email", email);
		paramMap.put("locked", locked);
		accountDao.updateLocked(paramMap);
	}
	
	//회원가입 시 이메일 중복 체크
	public void emailCheck(String email) {
		if(accountDao.findEmail(email) != 0) {
			throw new EmailAlreadyUsedException();
		}
	}
	
	//계정 찾기
	public List<AccountFindResponseDto> findAccount(AccountFindRequestDto dto) throws AccountNotFoundException {
		List<AccountFindResponseDto> list = accountDao.findAccount(dto);
		if(list.isEmpty()) {
			throw new AccountNotFoundException();
		}
		return list;
	}
	
	//인증 이메일 재발송
	public void resendEmail(String email) throws Exception {
		AuthDto authDto = updateAuth(email);
		sendEmail(authDto);
	}
	
	public void sendResetEmail(AccountPasswordResetRequestDto dto) throws Exception {
		AuthDto authDto = createAuth(dto.getEmail(), AuthOption.RESET);
		sendEmail(authDto);
	}
	
	//비밀번호 재설정(초기화)
	@Transactional
	public void resetPassword(AccountPasswordResetDto dto) {
		System.out.println(dto.toString());
		AuthDto authDto = accountDao.findByAuth(dto.getEmail());
		
		if(authDto == null || authDto.isAuthExpired() || !dto.getAuthKey().equals(authDto.getAuthKey()) || !dto.getAuthOption().equals(AuthOption.RESET.getOption())) {
			throw new BadAuthRequestException();
		}
		//비밀번호 재설정 
		Account account = accountDao.findByEmail(dto.getEmail());
		account.setPassword(passwordEncoder.encode(dto.getPassword()));
		accountDao.updatePassword(account);
		//인증키 파기
		accountDao.authKeyExpired(dto.toAuthDto());
	}
	
	//이메일 인증 링크를 통해 인증키 비교 후 상태값 변경 
	@Transactional
	public void emailConfirm(AccountAuthRequestDto dto) {
		System.out.println(dto.toString());
		AuthDto authDto = accountDao.findByAuth(dto.getEmail());
		
		if(authDto == null || authDto.isAuthExpired() || !dto.getAuthKey().equals(authDto.getAuthKey()) || !dto.getAuthOption().equals(AuthOption.SIGNUP.getOption())) {
			throw new BadAuthRequestException();
		}
		accountDao.activetion(dto.getEmail());
		accountDao.authKeyExpired(dto);
	}
	
	// 비밀번호 재설정을 위한 이메일을 통한 인증
	public void resetPasswordConfirm(AccountAuthRequestDto dto) {
		System.out.println(dto.toString());
		AuthDto authDto = accountDao.findByAuth(dto.getEmail());
		
		if (authDto == null || authDto.isAuthExpired() || !dto.getAuthKey().equals(authDto.getAuthKey()) || !dto.getAuthOption().equals(AuthOption.RESET.getOption())) {
			throw new BadAuthRequestException();
		}
	}

	@Transactional
	private AuthDto createAuth(String email, AuthOption authOption) {
		Map<String, String> paramMap = new HashMap<>();
		paramMap.put("email", email);
		paramMap.put("authOption", authOption.getOption());
		
		AuthDto authDto = null;
		
		if(accountDao.authCheck(paramMap) == 0) {
			String authKey = new AuthKey().getKey();
			authDto = new AuthDto(email, authKey, authOption.getOption());
			accountDao.authSave(authDto);
		}else {
			authDto = updateAuth(email);
		}
		return authDto;
	}
	
	@Transactional
	private AuthDto updateAuth(String email) {
		AuthDto authDto = accountDao.findByAuth(email);
		
		if(authDto == null) {
			throw new BadAuthRequestException();
		}
		
		String authKey = new AuthKey().getKey();
		authDto.setAuthKey(authKey);
		
		accountDao.updateAuthKey(authDto);
		return authDto;
	}
	
	private void sendEmail(AuthDto authDto) throws Exception {
		emailService.sendEmail(authDto);
	}
	
}
