package jsh.project.board.account.dto;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jsh.project.board.account.exception.EmailNotCheckedException;

@SuppressWarnings("serial")
public class Account implements UserDetails{
	
	private String email;
	private String password;
	private String nickname;
	private int authentication;
	private boolean enabled;
	private String role;
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		ArrayList<GrantedAuthority> auth = new ArrayList<GrantedAuthority>();
		auth.add(new SimpleGrantedAuthority(role));
		return auth;
	}

	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public String getUsername() {
		return email;
	}
	
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	
	public String getNickname() {
		return nickname;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return enabled;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	public void setAuthentication(int authentication) {
		this.authentication = authentication;
	}
	
	public int getAuthentication() {
		return authentication;
	}
	
	//이메일 인증여부 
	public boolean authenticationCheck() {
		if(authentication == 0) {
			return false;
		}
		return true;
	}

}
