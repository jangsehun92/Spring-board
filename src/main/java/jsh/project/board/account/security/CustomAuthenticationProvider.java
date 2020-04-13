package jsh.project.board.account.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;

import jsh.project.board.account.dto.Account;

public class CustomAuthenticationProvider implements AuthenticationProvider {

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
		//..
		
		
		return new UsernamePasswordAuthenticationToken(account, password, account.getAuthorities());
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return true;
	}

}
