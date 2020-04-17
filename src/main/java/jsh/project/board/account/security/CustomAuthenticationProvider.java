package jsh.project.board.account.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;

import jsh.project.board.account.controller.AccountController;
import jsh.project.board.account.dto.Account;
import jsh.project.board.account.exception.EmailNotCheckedException;

public class CustomAuthenticationProvider implements AuthenticationProvider {
	private static final Logger log = LoggerFactory.getLogger(CustomAuthenticationProvider.class);

	@Autowired
	private CustomUserDetailsService userDetailsService;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		String email = (String) authentication.getPrincipal();
		String password = (String) authentication.getCredentials();

		Account account = (Account)userDetailsService.loadUserByUsername(email);
		
		//여러가지 상황에 따른 Exception
		if (!passwordEncoder.matches(password, account.getPassword())) {
			throw new BadCredentialsException("비밀번호 불일치");
		}
		
		//email 인증 여부 체크
		//account.getAuthentication();
		//..
		
		
		return new UsernamePasswordAuthenticationToken(account, password, account.getAuthorities());
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return true;
	}

}
