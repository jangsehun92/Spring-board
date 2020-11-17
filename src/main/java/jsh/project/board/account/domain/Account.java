package jsh.project.board.account.domain;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jsh.project.board.account.dto.request.RequestAccountCreateDto;
import jsh.project.board.account.dto.request.RequestAccountResetDto;
import jsh.project.board.account.enums.Role;

@SuppressWarnings("serial")
public class Account implements UserDetails {
	private int id;
	private String email;
	private String password;
	private String name;
	private String birth;
	private String nickname;
	private boolean locked;
	private boolean enabled;
	private String role;
	private int failureCount;
	private Date regdate;
	private Date lastLoginDate;

	private Account(Integer id, String email, String password, String name, String birth, String nickname, Boolean locked, Boolean enabled, String role, Integer failureCount, Date regdate, Date lastLoginDate) {
		this.id = id;
		this.email = email;
		this.password = password;
		this.name = name;
		this.birth = birth;
		this.nickname = nickname;
		this.locked = locked;
		this.enabled = enabled;
		this.role = role;
		this.failureCount = failureCount;
		this.regdate = regdate;
		this.lastLoginDate = lastLoginDate;
	}

	private Account(RequestAccountCreateDto dto, Role role) {
		this.email = dto.getEmail();
		this.password = dto.getPassword();
		this.name = dto.getName();
		this.birth = dto.getBirth();
		this.nickname = dto.getNickname();
		this.role = role.getValue(); 
	}
	
	//정적 팩토리 메소드
	public static Account of(RequestAccountCreateDto dto, Role role) {
		return new Account(dto,role);
	}
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		ArrayList<GrantedAuthority> auth = new ArrayList<GrantedAuthority>();
		auth.add(new SimpleGrantedAuthority(role));
		return auth;
	}

	public int getId() {
		return id;
	}

	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public String getUsername() {
		return email;
	}

	public String getName() {
		return name;
	}

	public String getBirth() {
		return birth;
	}

	public String getNickname() {
		return nickname;
	}

	public int getFailureCount() {
		return failureCount;
	}

	public Date getRegdate() {
		return regdate;
	}

	public Date getLastLoginDate() {
		return lastLoginDate;
	}
	
	@Override
	public boolean isAccountNonExpired() {
		return true;
	}
	
	//true = 계정이 잠기지 않은 상태
	@Override
	public boolean isAccountNonLocked() {
		return locked;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	//true = 계정이 활성화된 상태
	@Override
	public boolean isEnabled() {
		return enabled;
	}

	public boolean check(RequestAccountResetDto dto) {
		if (!this.name.equals(dto.getName()) || !this.birth.equals(dto.getBirth())) {
			return false;
		}
		return true;
	}
	
	public void changeAccountNickname(String nickname) {
		this.nickname = nickname;
	}
	
	public void changeAccountPassword(String password) {
		this.password = password;
	}
	
	@Override
	public String toString() {
		return "Account { id : " + id + " email : " + email + " password : " + password + " name : " + name + " birth : " + birth 
				+ " nickname : " + nickname  + " locked : " + locked + " enabled : " + enabled + " role : " + role + " failureCount : " + failureCount 
				+ " regdate : " + regdate + " lastLoginDate : " + lastLoginDate + " } "  ;
	}
	
}
