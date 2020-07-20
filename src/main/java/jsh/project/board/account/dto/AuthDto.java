package jsh.project.board.account.dto;

import java.util.Date;

public class AuthDto {
	private String email;
	private String authKey;
	private String authOption;
	private boolean expired;
	private Date expiredDate;
	
	public AuthDto() {
		
	}
	
	public AuthDto(String email, String authKey, String authOption) {
		this.email = email;
		this.authKey = authKey;
		this.authOption = authOption;
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

	public void setExpired(boolean epried) {
		this.expired = epried;
	}
	
	public boolean isAuthExpired() {
		return expired;
	}
	
	public String getAuthOption() {
		return authOption;
	}
	
	public void setExpiredDate(Date expiredDate) {
		this.expiredDate = expiredDate;
	}
	
	public Date getExpiredDate() {
		return expiredDate;
	}

}
