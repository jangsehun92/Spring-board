package jsh.project.board.account.domain;

import java.util.Date;
import java.util.ArrayList;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jsh.project.board.account.dto.request.RequestAccountResetDto;

@SuppressWarnings("serial")
public class Account implements UserDetails {
	private int id;
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

	public Account() {

	}

	public Account(String email, String password, String name, String birth, String nickname, String role) {
		this.email = email;
		this.password = password;
		this.name = name;
		this.birth = birth;
		this.nickname = nickname;
		this.role = role;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getBirth() {
		return birth;
	}

	public void setBirth(String birth) {
		this.birth = birth;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getNickname() {
		return nickname;
	}

	public void setLocked(int locked) {
		this.locked = locked;
	}

	public int getFailureCount() {
		return failureCount;
	}

	public void setFailureCount(int failureCount) {
		this.failureCount = failureCount;
	}

	public Date getRegdate() {
		return regdate;
	}

	public void setRegdate(Date regdate) {
		this.regdate = regdate;
	}

	public Date getLastLoginDate() {
		return lastLoginDate;
	}
	
	public void setLastLoginDate(Date lastLoginDate) {
		this.lastLoginDate = lastLoginDate;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	// true = 계정이 잠겨 있지않다.
	@Override
	public boolean isAccountNonLocked() {
		if (locked == 0) {
			return true;
		}
		return false;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return enabled;
	}

	public boolean findAccountCheck(RequestAccountResetDto dto) {
		if (!this.name.equals(dto.getName()) || !this.birth.equals(dto.getBirth())) {
			return false;
		}
		return true;
	}
	
	public interface AccountConverter{
		Account toAccount();
	}

}
