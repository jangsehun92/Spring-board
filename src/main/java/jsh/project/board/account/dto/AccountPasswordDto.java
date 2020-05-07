package jsh.project.board.account.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import jsh.project.board.account.exception.PasswordCheckFailedException;

public class AccountPasswordDto {
	
	@NotBlank(message = "이전 비밀번호를 입력해주세요.")
	private String beforePassword;
	@Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*\\W)(?=\\S+$).{8,20}$", message = "최소 8자리의 소문자,대문자,숫자,특수문자가 포함되어야합니다. ")
	private String afterPassword;
	@NotBlank(message = "바꿀 비밀번호를 재입력해주세요.")
	private String afterPasswordCheck;
	
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
	
	public String getAfterPasswordCheck() {
		return afterPasswordCheck;
	}
	
	public void setAfterPasswordCheck(String afterPasswordCheck) {
		this.afterPasswordCheck = afterPasswordCheck;
	}
	
	public void checkPassword() {
		if(!afterPassword.equals(afterPasswordCheck)) {
			throw new PasswordCheckFailedException();
		}
	}
	
	

}
