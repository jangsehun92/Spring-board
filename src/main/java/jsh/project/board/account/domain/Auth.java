package jsh.project.board.account.domain;

import java.util.Date;

import jsh.project.board.account.dto.request.RequestEmailConfirmDto;
import jsh.project.board.account.dto.request.RequestPasswordResetDto;
import jsh.project.board.account.enums.AuthOption;
import jsh.project.board.global.infra.util.AuthKeyMaker;

public class Auth {
	private String email;
	private String authKey;
	private String authOption;
	private boolean expired;
	private Date expiredDate;
	
	private Auth() {
		
	}
	
	private Auth(final String email, final String authKey, final String authOption) {
		this.email = email;
		this.authKey = authKey;
		this.authOption = authOption;
	}
	
	public static Auth of(final Account account, final AuthKeyMaker authKeyMaker, final AuthOption authOption) {
		return new Auth(account.getUsername(), authKeyMaker.getKey(), authOption.getValue());
	}
	
	public static Auth from(Object obj) {
		String email = "";
		String authKey = "";
		String authOption = "";
		
		if(obj instanceof RequestPasswordResetDto) {
			RequestPasswordResetDto dto = (RequestPasswordResetDto)obj;
			email = dto.getEmail();
			authKey = dto.getAuthKey();
			authOption = dto.getAuthOption();
		}
		
		if(obj instanceof RequestEmailConfirmDto) {
			RequestEmailConfirmDto dto = (RequestEmailConfirmDto) obj;
			email = dto.getEmail();
			authKey = dto.getAuthKey();
			authOption = dto.getAuthOption();
		}
		
		return new Auth(email, authKey, authOption);
	}
	
	public String getEmail() {
		return email;
	}

	public String getAuthKey() {
		return authKey;
	}

	public String getAuthOption() {
		return authOption;
	}

	public boolean getExpired() {
		return expired;
	}
	
	public Date getExpiredDate() {
		return expiredDate;
	}
	
	public void updateAuthKey() {
		this.authKey = new AuthKeyMaker().getKey();
	}

}
