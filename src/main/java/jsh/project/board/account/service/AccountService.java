package jsh.project.board.account.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jsh.project.board.account.dao.AccountDao;
import jsh.project.board.account.dto.Account;
import jsh.project.board.account.dto.AccountCreateDto;
import jsh.project.board.account.dto.AccountEmailDto;
import jsh.project.board.account.exception.EmailAlreadyCheckedException;
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
		AccountEmailDto accountEmailDto = getAccountEmailDto(dto.getEmail(), "singup");
		//이메일인증관련 테이블에 이메일과 인증키 저장
		accountDao.authKeyCreate(accountEmailDto);
		//인증 이메일 발송
		sendEmail(accountEmailDto);
	}
	
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
	
	//계정 Lock 및 해제
	public void updateLocked(String email, int locked) {
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("email", email);
		paramMap.put("locked", locked);
		accountDao.updateLocked(paramMap);
	}
	
	//이메일 중복 체크
	public void duplicateCheck(String email) {
		if(accountDao.findEmail(email) == 1) {
			throw new EmailAlreadyUsedException();
		}
	}
	
	@Transactional
	public void resendEmail(String email) throws Exception {
		//리팩토링 하기
		
		//이메일&인증여부 체크
		Account account = accountDao.findByEmail(email);
		String option = null;
		//AccountCheckDto accountCheckDto = accountDao.accountInfo(email);
		
		//이미 인증이 완료된 계정
		if(account.isEnabled()) {
			throw new EmailAlreadyCheckedException();
		}
		
		//이메일 인증이 안되있는 계정
		if(!account.isEnabled()) {
			option = "singup";
		}
		
		//잠긴 계정
		if(account.isAccountNonLocked()) {
			option = "locked";
		}
		
		//해당 이메일에 대한 인증키 재생성
		AccountEmailDto accountEmailDto = getAccountEmailDto(email, option);
		//인증키 재설정
		accountDao.updateAuthKey(accountEmailDto);
		//이메일 발송
		sendEmail(accountEmailDto);
	}
	
	//이메일 인증 링크를 통해 인증키 비교 후 상태값 변경 
	@Transactional
	public void emailConfirm(AccountEmailDto dto) {
		if(accountDao.findByAuthKey(dto.getEmail()).equals(dto.getAuthKey())) {
			accountDao.emailChecked(dto.getEmail());
			accountDao.authKeyExpired(dto);
		}
	}

	private AccountEmailDto getAccountEmailDto(String email, String option) {
		//64자리 인증키 생성
		String authKey = new AuthKey().getKey();
		//이메일,인증키 dto생성
		AccountEmailDto accountEmailDto = new AccountEmailDto(email, authKey, option);
		return accountEmailDto;
	}
	
	//이메일 발송
	private void sendEmail(AccountEmailDto dto) throws Exception {
		emailService.sendEmail(dto);
	}
	
}
