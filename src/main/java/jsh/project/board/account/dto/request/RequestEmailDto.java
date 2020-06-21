package jsh.project.board.account.dto.request;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

public class RequestEmailDto {
	@NotBlank(message = "이메일을 입력해주세요. ")
	@Email(message = "올바른 형식의 이메일 주소여야합니다. ")
	private String email;
	
	public RequestEmailDto() {
		
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	

}
