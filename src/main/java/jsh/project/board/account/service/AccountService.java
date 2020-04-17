package jsh.project.board.account.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jsh.project.board.account.dao.AccountDao;
import jsh.project.board.account.dto.AccountCreateDto;
import jsh.project.board.account.dto.AccountEmailDto;
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
		
		//64자리 인증키 생성
		String authKey = new AuthKey().getKey();
		
		//이메일,인증키 dto생성
		AccountEmailDto accountEmailDto = new AccountEmailDto(dto.getEmail(), authKey);
		System.out.println("이메일 : " + accountEmailDto.getEmail());
		System.out.println("인증키 : " + accountEmailDto.getAuthKey());
		
		//계정 정보 저장
		accountDao.save(dto);
		//이메일인증관련 테이블에 이메일과 인증키 저장
		accountDao.create(accountEmailDto);
		//인증 이메일 발송
		sendEmail(accountEmailDto);
		
	}
	
	//이메일 중복 체크
	public void checkEmail(String email) {
		if(accountDao.findByEmail(email) == 1) {
			throw new EmailAlreadyUsedException();
		}
	}
	
	//이메일 발송
	public void sendEmail(AccountEmailDto dto) throws Exception {
		emailService.sendEmail(dto);
	}
	
	//이메일 인증 링크를 통해 인증키 비교 후 상태값 변경 
	@Transactional
	public void emailConfirm(AccountEmailDto dto) {
		if(accountDao.authKeySearch(dto.getEmail()).equals(dto.getAuthKey())) {
			accountDao.emailChecked(dto.getEmail());
			accountDao.expired(dto);
		}
	}
	

}
