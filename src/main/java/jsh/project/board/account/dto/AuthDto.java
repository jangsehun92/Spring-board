package jsh.project.board.account.dto;

public class AuthDto {
	private String email;
	private String authKey;
	private String authOption;
	private int expired;
	
	public AuthDto() {
		
	}
	
	public AuthDto(String email, String authKey, String authOption) {
		this.email = email;
		this.authKey = authKey;
		this.authOption = authOption;
		this.expired = 0;
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

	public void setAuthOption(String authOption) {
		this.authOption = authOption;
	}

	public void setExpired(int epried) {
		this.expired = epried;
	}
	
	public boolean isAuthExpired() {
		if(expired == 1) {
			return true;
		}
		return false;
	}
	
	public String isAuthOption() {
		return authOption;
	}

}
