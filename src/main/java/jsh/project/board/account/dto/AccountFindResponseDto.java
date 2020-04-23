package jsh.project.board.account.dto;

import java.sql.Date;

public class AccountFindResponseDto {
	private String email;
	private Date regdate;

	public AccountFindResponseDto() {
		
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Date getRegdate() {
		return regdate;
	}

	public void setRegdate(Date regdate) {
		this.regdate = regdate;
	}
	
	@Override
	public String toString() {
		return "AccountFindResponseDto { email : "+ email + " regdate : " + regdate + " }";
	}
	
}
