package jsh.project.board.account.domain;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;

import jsh.project.board.account.dto.request.RequestAccountCreateDto;
import jsh.project.board.account.dto.request.RequestAccountResetDto;
import jsh.project.board.account.dto.request.RequestPasswordDto;
import jsh.project.board.account.dto.request.RequestPasswordResetDto;
import jsh.project.board.account.enums.Role;
import jsh.project.board.account.exception.PasswordCheckFailedException;
import jsh.project.board.account.exception.PasswordNotMatchException;

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
	
	//Mybatis는 ResultMap(ResultType)에 명시된 객체를 생성하기 위해 기본생성자를 이용해 객체를 생성하고, 리플렉션으로 필드 주입을 한다.
	//(리플렉션으로 접근 제어를 풀기 때문에 접근제한자가 private 여도 가능하다.)
	private Account() {
		
	}

	private Account(String email, String password, String name, String birth, String nickname, String role) {
		this.email = email;
		this.password = password; 
		this.name = name; 
		this.birth = birth; 
		this.nickname = nickname;
		this.role = role; 
	}
	
	//정적 팩토리 메소드
	public static Account of(PasswordEncoder passwordEncoder, RequestAccountCreateDto dto, Role role) {
		if(!dto.getPassword().equals(dto.getPasswordCheck())) {
			throw new PasswordCheckFailedException();
		}
		return new Account(dto.getEmail(), passwordEncoder.encode(dto.getPassword()), dto.getName(), dto.getBirth(), dto.getNickname(), role.getValue());
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

	public boolean checkAccount(RequestAccountResetDto dto) {
		if (!this.name.equals(dto.getName()) || !this.birth.equals(dto.getBirth())) {
			return false;
		}
		return true;
	}
	
	public void changeNickname(String nickname) {
		this.nickname = nickname;
	}
	
	public void changePassword(PasswordEncoder passwordEncoder, RequestPasswordDto dto) {
		if(!dto.getAfterPassword().equals(dto.getAfterPasswordCheck())) 
			throw new PasswordCheckFailedException();
		if(!passwordEncoder.matches(dto.getBeforePassword(), this.password))  
			throw new PasswordNotMatchException();
		this.password = passwordEncoder.encode(dto.getAfterPassword());
	}
	
	public void resetPassword(PasswordEncoder passwordEncoder, RequestPasswordResetDto dto) {
		if(!dto.getPassword().equals(dto.getPasswordCheck())) 
			throw new PasswordCheckFailedException();
		this.password = passwordEncoder.encode(dto.getPassword());
	}
	
	@Override
	public String toString() {
		return "Account { id : " + id + " email : " + email + " password : " + password + " name : " + name + " birth : " + birth 
				+ " nickname : " + nickname  + " locked : " + locked + " enabled : " + enabled + " role : " + role + " failureCount : " + failureCount 
				+ " regdate : " + regdate + " lastLoginDate : " + lastLoginDate + " } "  ;
	}

}
