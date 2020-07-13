package jsh.project.board.account.dto.request;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import jsh.project.board.account.domain.Account;
import jsh.project.board.account.domain.Account.AccountConverter;
import jsh.project.board.account.enums.Role;
import jsh.project.board.account.exception.PasswordCheckFailedException;

public class RequestAccountCreateDto implements AccountConverter{
	@NotBlank(message = "이메일을 입력해주세요. ")
	@Email(message = "올바른 형식의 이메일 주소여야합니다. ")
	private String email;
	@Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*\\W)(?=\\S+$).{8,20}$", message = "최소 8자리의 소문자,대문자,숫자,특수문자가 포함되어야합니다. ")
	private String password;
	@NotBlank(message = "비밀번호를 재입력해주세요.")
	private String passwordCheck;
	@NotBlank(message = "이름을 입력해주세요.")
	private String name;
	@Pattern(regexp ="[0-9]{6,6}", message = "주민번호 앞자리를 입력해주세요. ")
	private String birth;
	@NotBlank(message = "닉네임을 입력해주세요.")
	private String nickname;
	private String role;
	
	public RequestAccountCreateDto() {
		
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
	
	public String getPasswordCheck() {
		return password;
	}

	public void setPasswordCheck(String passwordCheck) {
		this.passwordCheck = passwordCheck;
	}
	
	public void setName(String name) {
		this.name = name.trim();
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
		this.nickname = nickname.trim();
	}
	
	public String getRole() {
		return role;
	}
	
	public void setRole(Role role) {
		this.role = role.getValue();
	}
	
	public void checkPassword() {
		if(!password.equals(passwordCheck)) {
			throw new PasswordCheckFailedException();
		}
	}
	
	@Override
	public Account toAccount() {
		return new Account(email, password, name, birth, nickname, role);
	}
	
	@Override
	public String toString() {
		return "AccountCreateDto { email : + " + email + "password : " + password + "passwordCheck : " + passwordCheck + " name : + " + name + " birth : " + birth + " nickname : " + nickname + "} ";
	}
	
	

}
