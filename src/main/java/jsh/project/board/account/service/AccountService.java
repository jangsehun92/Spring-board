package jsh.project.board.account.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import jsh.project.board.account.dao.AccountDao;
import jsh.project.board.account.dto.Account;

@Service
public class AccountService implements UserDetailsService{
	
	@Autowired
	private AccountDao accountDao;
	
	public AccountService() {
		
	}
	
	public void save() {
		
	}

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		Account account = accountDao.getUserByEmail(email);
		return account;
	}

	

}
