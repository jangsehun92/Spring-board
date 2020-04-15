package jsh.project.board.account.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import jsh.project.board.account.dao.AccountDao;
import jsh.project.board.account.dto.AccountCreateDto;
import jsh.project.board.account.exception.EmailAlreadyUsedException;

@Service
public class AccountService{
	
	private AccountDao accountDao;
	private PasswordEncoder passwordEncoder;
	
	public AccountService(AccountDao accountDao, PasswordEncoder passwordEncoder) {
		this.accountDao = accountDao;
		this.passwordEncoder = passwordEncoder;
	}
	
	public void register(AccountCreateDto dto) {
		//DB에 저장하기 전에 권한 설정 과 비밀번호 암호화를 해준다.
		dto.setPassword(passwordEncoder.encode(dto.getPassword()));
		dto.setRole("ROLE_USER");
		
		accountDao.save(dto);
	}
	
	public boolean checkEmail(String email) {
		//System.out.println(accountDao.findByEmail(email));
		if(accountDao.findByEmail(email) == 1) {
			throw new EmailAlreadyUsedException();
		}
		System.out.println("checkEmail?");
		return true; 
	}
	

}
