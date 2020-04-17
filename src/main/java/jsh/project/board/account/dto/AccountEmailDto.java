package jsh.project.board.account.dto;

public class AccountEmailDto {
	private String email;
	private String authKey;
	
	public AccountEmailDto() {
		
	}
	
	public AccountEmailDto(String email, String authKey) {
		this.email = email;
		this.authKey = authKey;
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

	public void setAuthKey(String authKye) {
		this.authKey = authKye;
	}
	
	

}
