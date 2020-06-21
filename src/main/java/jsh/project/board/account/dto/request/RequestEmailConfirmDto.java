package jsh.project.board.account.dto.request;

import java.util.HashMap;
import java.util.Map;

public class RequestEmailConfirmDto {
	private String email;
	private String authKey;
	private String authOption;
	
	public RequestEmailConfirmDto() {
		
	}
	
	public RequestEmailConfirmDto(String email, String authKey, String authOption) {
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

	public void setAuthKey(String authKye) {
		this.authKey = authKye;
	}
	
	public String getAuthOption() {
		return authOption;
	}
	
	public void setAuthOption(String option) {
		this.authOption = option;
	}
	
	public Map<String, String> toMap(){
		Map<String, String> paramMap = new HashMap<>();
		paramMap.put("email", email);
		paramMap.put("authOption", authOption);
		return paramMap;
	}
	
	@Override
	public String toString() {
		return "email : " + email + " authKey = " + authKey + " authOption : " + authOption;
	}
	
	

}
