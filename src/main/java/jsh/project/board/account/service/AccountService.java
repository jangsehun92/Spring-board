package jsh.project.board.account.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import jsh.project.board.account.dao.AccountDao;
import jsh.project.board.account.dto.Account;

@Service
public class AccountService implements UserDetailsService{
	
	@Autowired
	private AccountDao accountDao;
	
//	@Autowired
//	private PasswordEncoder passwordEncoder;
	
	public AccountService() {
		
	}
	
	public void save() {
		//권한 설정 과 비밀번호 암호화를 해야한다. 
	}
	
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		Account account = accountDao.getUserByEmail(email);
//		account.setPassword(passwordEncoder.encode(account.getPassword()));
		return account;
	}

	

}
