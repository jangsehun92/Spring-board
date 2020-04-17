package jsh.project.board.account.dto;

public class AccountCheckDto {
	
	private String email;
	private int authentication;
	
	public AccountCheckDto() {
		
	}
	
//	public AccountCheckDto(String email, int authentication) {
//		this.email = email;
//		this.authentication = authentication;
//	}

//	public String getEmail() {
//		return email;
//	}
//
//	public void setEmail(String email) {
//		this.email = email;
//	}
//
//	public int getAuthentication() {
//		return authentication;
//	}
//
//	public void setAuthentication(int authentication) {
//		this.authentication = authentication;
//	}
	
	public boolean check() {
		if(authentication == 0) {
			return false;
		}
		return true;
	}
	
	
	
	

}
