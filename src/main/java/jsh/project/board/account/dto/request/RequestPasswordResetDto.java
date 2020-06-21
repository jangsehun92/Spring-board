package jsh.project.board.account.dto.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import jsh.project.board.account.exception.PasswordCheckFailedException;

public class RequestPasswordResetDto {
	
	private String email;
	@Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*\\W)(?=\\S+$).{8,20}$", message = "최소 8자리의 소문자,대문자,숫자,특수문자가 포함되어야합니다. ")
	private String password;
	@NotBlank(message = "비밀번호를 재입력해주세요.")
	private String passwordCheck;
	private String authKey;
	private String authOption;
	
	public RequestPasswordResetDto() {
		
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
		return passwordCheck;
	}
	
	public void setPasswordCheck(String passwordCheck) {
		this.passwordCheck = passwordCheck;
	}

	public String getAuthKey() {
		return authKey;
	}

	public void setAuthKey(String authKey) {
		this.authKey = authKey;
	}

	public String getAuthOption() {
		return authOption;
	}

	public void setAuthOption(String authOption) {
		this.authOption = authOption;
	}
	
	public RequestEmailConfirmDto toAuthDto() {
		RequestEmailConfirmDto accountAuthRequestDto = new RequestEmailConfirmDto(email, authKey, authOption);
		return accountAuthRequestDto;
	}
	
	public void checkPassword() {
		if(!password.equals(passwordCheck)) {
			throw new PasswordCheckFailedException();
		}
	}
	
	@Override
	public String toString() {
		return "AccountPasswordResetDto { email : " + email + ", password : " + password + " authKey : " + authKey + " authOption : " + authOption + " } ";
	}
	
	

}
