package jsh.project.board.account.dto;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@SuppressWarnings("serial")
public class Account implements UserDetails{
	
	private String email;
	private String password;
	private String name;
	private String birth;
	private String nickname;
	private int authentication;
	private boolean enabled;
	private String role;
	private int failureCount;
	private Date regdate;
	private Date lastLoginDate;
	
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
	
	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public String getUsername() {
		return email;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	
	public void setBirth(String birth) {
		this.birth = birth;
	}
	
	public String getBirth() {
		return birth;
	}
	
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	
	public String getNickname() {
		return nickname;
	}
	
	public void setAuthentication(int authentication) {
		this.authentication = authentication;
	}
	
	public int getAuthentication() {
		return authentication;
	}
	
	public void setFailureCount(int failureCount) {
		this.failureCount = failureCount;
	}
	
	public int getFailureCount() {
		return failureCount;
	}
	
	public void setRegdate(Date regdate) {
		this.regdate = regdate;
	}
	
	public Date getRegdate() {
		return regdate;
	}
	
	public void setLastLoginDate(Date lastLoginDate) {
		this.lastLoginDate = lastLoginDate;
	}
	
	public Date getLastLoginDate() {
		return lastLoginDate;
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
	
	//이메일 인증여부 
	public boolean authenticationCheck() {
		if(authentication == 0) {
			return false;
		}
		return true;
	}

}
