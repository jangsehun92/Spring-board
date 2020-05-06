package jsh.project.board.account.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

public class AccountPasswordResetRequestDto {
	
	@NotBlank(message = "이메일을 입력해 주세요.")
	@Email
	private String email;
	@NotBlank(message = "이름을 입력해주세요.")
	private String name;
	@NotBlank(message = "생년월일을 입력해주세요.")
	private String birth;
	
	public AccountPasswordResetRequestDto() {
		
	}
	
	public AccountPasswordResetRequestDto(String email, String name, String birth) {
		this.email = email;
		this.name = name;
		this.birth = birth;
	}
	
	
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
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

	@Override
	public String toString() {
		return "AccountPasswordChangeDto { email : " + email +", name : " + name + " birth : " + birth +" } ";
	}
}
