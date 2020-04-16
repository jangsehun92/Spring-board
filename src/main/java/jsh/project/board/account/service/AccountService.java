package jsh.project.board.account.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import jsh.project.board.account.dao.AccountDao;
import jsh.project.board.account.dto.AccountCreateDto;
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
	
	public void register(AccountCreateDto dto) throws Exception {
		//DB에 저장하기 전에 권한 설정 과 비밀번호 암호화 처리
		dto.setPassword(passwordEncoder.encode(dto.getPassword()));
		dto.setRole("ROLE_USER");
		
		//64자리 인증키 생성
		String authKey = new AuthKey().getKey();
		
		//계정 정보 저장
		accountDao.save(dto);
		//인증 이메일 발송
		emailService.sendEmail(dto.getEmail(), authKey);
		//이메일인증관련 테이블에 이메일과 인증키 저장
		
	}
	
	public void checkEmail(String email) {
		if(accountDao.findByEmail(email) == 1) {
			throw new EmailAlreadyUsedException();
		}
	}
	

}
