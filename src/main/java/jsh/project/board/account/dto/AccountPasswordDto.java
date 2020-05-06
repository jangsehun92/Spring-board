package jsh.project.board.account.dto;

import javax.validation.constraints.NotBlank;

public class AccountPasswordDto {
	
	@NotBlank(message = "이전 비밀번호를 입력해주세요.")
	private String beforePassword;
	@NotBlank(message = "바꿀 비밀번호를 입력해주세요.")
	private String afterPassword;
	
	public AccountPasswordDto() {
		
	}

	public String getBeforePassword() {
		return beforePassword;
	}

	public void setBeforePassword(String beforePassword) {
		this.beforePassword = beforePassword;
	}

	public String getAfterPassword() {
		return afterPassword;
	}

	public void setAfterPassword(String afterPassword) {
		this.afterPassword = afterPassword;
	}
	
	

}
