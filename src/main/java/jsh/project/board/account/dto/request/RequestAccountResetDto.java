package jsh.project.board.account.dto.request;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

public class RequestAccountResetDto {
	
	@NotBlank(message = "이메일을 입력해 주세요.")
	@Email(message = "올바른 형식의 이메일 주소여야합니다. ")
	private String email;
	@NotBlank(message = "이름을 입력해주세요.")
	private String name;
	@NotBlank(message = "생년월일을 입력해주세요.")
	private String birth;
	
	public RequestAccountResetDto() {
		
	}
	
	public RequestAccountResetDto(String email, String name, String birth) {
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
