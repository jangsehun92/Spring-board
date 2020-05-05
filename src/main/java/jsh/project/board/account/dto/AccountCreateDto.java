package jsh.project.board.account.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

public class AccountCreateDto {
	
	@NotBlank(message = "이메일을 입력해주세요.")
	@Email
	private String email;
	@NotBlank(message = "비밀번호를 입력해주세요.")
	private String password;
	@NotBlank(message = "이름을 입력해주세요.")
	private String name;
	@NotBlank(message = "생년월일을 입력해주세요.")
	private String birth;
	@NotBlank(message = "닉네임을 입력해주세요.")
	private String nickname;
	private String role;
	
	public AccountCreateDto() {
		
	}
	
	public AccountCreateDto(String email, String password, String name, String birth,String nickname) {
		this.email = email;
		this.password = password;
		this.name = name;
		this.birth = birth;
		this.nickname = nickname;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
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

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	
	public String getRole() {
		return role;
	}
	
	public void setRole(String role) {
		this.role = role;
	}
	
	@Override
	public String toString() {
		return "AccountCreateDto { email : + " + email + "password : " + password + " name : + " + name + " birth : " + birth + " nickname : " + nickname + "} ";
	}
	
	

}
