package jsh.project.board.account.dto;

public class AccountPasswordChangeDto {
	private String email;
	private String authKey;
	private String password;
	
	public AccountPasswordChangeDto() {
		
	}
	
	public AccountPasswordChangeDto(String email, String authKey, String password) {
		this.email = email;
		this.authKey = authKey;
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getAuthKey() {
		return authKey;
	}

	public void setAuthKey(String authKey) {
		this.authKey = authKey;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	@Override
	public String toString() {
		return "AccountPasswordChangeDto { email : " + email +", authKey : " + authKey + " password : " + password +" } ";
	}
}
