package jsh.project.board.account.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import jsh.project.board.account.Dao.AccountDao;
import jsh.project.board.account.dto.Account;

@Service
public class AccountService implements UserDetailsService{
	
	@Autowired
	private AccountDao accountDao;
	
	public AccountService() {
		
	}
	
	public AccountService(AccountDao accountDao) {
		this.accountDao = accountDao;
	}
	
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		System.out.println(email);
		System.out.println("서비스로 들어옴 !");
		Account account = accountDao.getUserByEmail(email);
		System.out.println("UserDetails.getAutorites : "+account.getAuthorities());
		return account;
	}

	

}
