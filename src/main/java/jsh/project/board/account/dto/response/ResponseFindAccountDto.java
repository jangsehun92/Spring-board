package jsh.project.board.account.dto.response;

import java.util.Date;

import jsh.project.board.account.domain.Account;

public class ResponseFindAccountDto {
	private String email;
	private Date regdate;

	private ResponseFindAccountDto(Account account) {
		this.email = account.getUsername();
		this.regdate = account.getRegdate();
	}
	
	public static ResponseFindAccountDto from(Account account) {
		return new ResponseFindAccountDto(account);
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
