package jsh.project.board.account.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import jsh.project.board.account.dao.AccountDao;
import jsh.project.board.account.dto.Account;
import jsh.project.board.account.dto.CreateAccountDto;

@Service
public class AccountService implements UserDetailsService{
	
	@Autowired
	private AccountDao accountDao;
	private PasswordEncoder passwordEncoder;
	
	public void register(CreateAccountDto dto) {
		//DB에 저장하기 전에 권한 설정 과 비밀번호 암호화를 해준다.
		dto.setPassword(passwordEncoder.encode(dto.getPassword()));
		dto.setRole("ROLE_USER");
		accountDao.save(dto);
	}
	
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		Account account = accountDao.getUserByEmail(email);
		return account;
	}

	

}
