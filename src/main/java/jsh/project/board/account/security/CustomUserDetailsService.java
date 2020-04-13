package jsh.project.board.account.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import jsh.project.board.account.dao.AccountDao;
import jsh.project.board.account.dto.Account;

public class CustomUserDetailsService implements UserDetailsService{
	
	@Autowired
	private AccountDao accountDao;

	@Override
	public Account loadUserByUsername(String email) throws UsernameNotFoundException {
		return accountDao.getUserByEmail(email);
	}

}
