package jsh.project.board.account.dto;

public class AccountPasswordResetDto {
	private String email;
	private String password;
	private String authKey;
	private String authOption;
	
	public AccountPasswordResetDto() {
		
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
	
	public AccountAuthRequestDto toAuthDto() {
		AccountAuthRequestDto accountAuthRequestDto = new AccountAuthRequestDto(email, authKey, authOption);
		return accountAuthRequestDto;
	}
	
	@Override
	public String toString() {
		return "AccountPasswordResetDto { email : " + email + ", password : " + password + " authKey : " + authKey + " authOption : " + authOption + " } ";
	}
	
	

}
