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
	private int locked;
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
	
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	
	public void setLocked(int locked) {
		this.locked = locked;
	}

	public void setFailureCount(int failureCount) {
		this.failureCount = failureCount;
	}
	
	public void setRegdate(Date regdate) {
		this.regdate = regdate;
	}
	
	public void setLastLoginDate(Date lastLoginDate) {
		this.lastLoginDate = lastLoginDate;
	}
	
	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		if(locked == 0) {
			return false;
		}
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
	
	//계정 잠김 여부 
//	public boolean isLocked() {
//		if(locked == 0) {
//			return false;
//		}
//		return true;
//	}

}
