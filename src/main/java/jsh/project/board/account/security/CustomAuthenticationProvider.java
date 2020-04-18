package jsh.project.board.account.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;

import jsh.project.board.account.dto.Account;
import jsh.project.board.global.error.exception.ErrorCode;

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
		if(account == null) {
			throw new InternalAuthenticationServiceException(ErrorCode.ACCOUNT_NOT_FOUND.getMessage());
		}
		
		if (!passwordEncoder.matches(password, account.getPassword())) {
			throw new BadCredentialsException(ErrorCode.ACCOUNT_LOGIN_FAILED.getMessage());
		}
		
		if(!account.authenticationCheck()) {
			throw new AuthenticationCredentialsNotFoundException(ErrorCode.EMAIL_NOT_CHECKED.getMessage());
		}
		//...
		
		return new UsernamePasswordAuthenticationToken(account, password, account.getAuthorities());
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return true;
	}

}
