package jsh.project.board.account.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;

import jsh.project.board.account.dto.Account;

public class CustomAuthenticationProvider implements AuthenticationProvider {
	//private static final Logger log = LoggerFactory.getLogger(CustomAuthenticationProvider.class);

	@Autowired
	private CustomUserDetailsService userDetailsService;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		String email = (String) authentication.getPrincipal();
		String password = (String) authentication.getCredentials();

		Account account = (Account)userDetailsService.loadUserByUsername(email);
		
		//비밀번호가 맞지 않다면
		if (!passwordEncoder.matches(password, account.getPassword())) {
			throw new BadCredentialsException(email);
		}
		
		//이메일 인증을 하지않았다면
		if(!account.isEnabled()) {
			throw new DisabledException(email);
		}
		
		//계정이 잠겨 있다면
		if(!account.isAccountNonLocked()) {
			throw new LockedException(email);
		}
		//...
		
		return new UsernamePasswordAuthenticationToken(account, password, account.getAuthorities());
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return true;
	}

}
