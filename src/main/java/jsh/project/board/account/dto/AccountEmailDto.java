package jsh.project.board.account.dto;

public class AccountEmailDto {
	private String email;
	private String authKey;
	private String option;
	
	public AccountEmailDto() {
		
	}
	
	public AccountEmailDto(String email, String authKey, String option) {
		this.email = email;
		this.authKey = authKey;
		this.option = option;
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
	
	public String getOption() {
		return option;
	}
	
	public void setOption(String option) {
		this.option = option;
	}
	
	

}
