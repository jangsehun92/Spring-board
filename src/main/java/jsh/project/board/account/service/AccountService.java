package jsh.project.board.account.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jsh.project.board.account.Enum.AuthOption;
import jsh.project.board.account.dao.AccountDao;
import jsh.project.board.account.dto.AccountAuthRequestDto;
import jsh.project.board.account.dto.AccountCreateDto;
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
	public void emailCheck(String email) {
		if(accountDao.findEmail(email) == 1) {
			throw new EmailAlreadyUsedException();
		}
	}
	
	//인증 이메일 재발송
	public void resendEmail(String email) throws Exception {
		AuthDto authDto = updateAuth(email);
		sendEmail(authDto);
	}
	
	//비밀번호 재설정을 위한인증 이메일 발송 서비스
	public void resendFindPassword(String email) throws Exception {
		AuthDto authDto = createAuth(email, AuthOption.LOCKED);
		sendEmail(authDto);
	}
	
	//이메일 인증 링크를 통해 인증키 비교 후 상태값 변경 
	@Transactional
	public void emailConfirm(AccountAuthRequestDto dto) {
		System.out.println(dto.toString());
		AuthDto authDto = accountDao.findByAuth(dto.getEmail());
		
		if(authDto == null) {
			throw new BadAuthRequestException();
		}
		
		if(authDto.isAuthExpired()) {
			throw new EmailAlreadyUsedException();
		}

		//인증키와 인증옵션이 맞아야 한다.
		if(dto.getAuthKey().equals(authDto.getAuthKey()) && dto.getAuthOption().equals(authDto.isAuthOption())) {
			accountDao.activetion(dto.getEmail());
			accountDao.authKeyExpired(dto);
		}
	}

	//회원가입, 비밀번호 재설정 시 auth테이블에 정보를 입력한다.(2군대에서만 이걸 호출)
	private AuthDto createAuth(String email, AuthOption authOption) {
		String authKey = new AuthKey().getKey();
		AuthDto authDto = new AuthDto(email, authKey, authOption.getOption());
		accountDao.authSave(authDto);
		return authDto;
	}
	
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
