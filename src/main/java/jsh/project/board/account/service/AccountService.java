package jsh.project.board.account.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jsh.project.board.account.dao.AccountDao;
import jsh.project.board.account.dto.AccountCheckDto;
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
		AccountEmailDto accountEmailDto = getAccountEmailDto(dto.getEmail());
		//이메일인증관련 테이블에 이메일과 인증키 저장
		accountDao.authKeyCreate(accountEmailDto);
		//인증 이메일 발송
		sendEmail(accountEmailDto);
		
	}
	
	//이메일 중복 체크
	public void duplicateCheck(String email) {
		if(accountDao.findEmail(email) == 1) {
			throw new EmailAlreadyUsedException();
		}
	}
	
	@Transactional
	public void resendEmail(String email) throws Exception {
		//이메일&인증여부 체크
		AccountCheckDto accountCheckDto = accountDao.accountInfo(email);
		if(accountCheckDto.check()) {
			throw new EmailAlreadyCheckedException();
		}
		//해당 이메일에 대한 인증키 재생성
		AccountEmailDto accountEmailDto = getAccountEmailDto(email);
		//인증키 재설정
		accountDao.authKeyUpdate(accountEmailDto);
		//이메일 발송
		sendEmail(accountEmailDto);
	}
	
	//이메일 인증 링크를 통해 인증키 비교 후 상태값 변경 
	@Transactional
	public void emailConfirm(AccountEmailDto dto) {
		if(accountDao.authKeySearch(dto.getEmail()).equals(dto.getAuthKey())) {
			accountDao.emailChecked(dto.getEmail());
			accountDao.authKeyExpired(dto);
		}
	}

	private AccountEmailDto getAccountEmailDto(String email) {
		//64자리 인증키 생성
		String authKey = new AuthKey().getKey();
		//이메일,인증키 dto생성
		AccountEmailDto accountEmailDto = new AccountEmailDto(email, authKey);
		return accountEmailDto;
	}
	
	//이메일 발송
	private void sendEmail(AccountEmailDto dto) throws Exception {
		emailService.sendEmail(dto);
	}
	
}
