package jsh.project.board.account.dto.response;

import java.util.Date;

import jsh.project.board.account.domain.Account;

public class ResponseFindAccountDto {
	private String email;
	private Date regdate;

	private ResponseFindAccountDto(String email, Date regdate) {
		this.email = email;
		this.regdate = regdate;
	}
	
	public static ResponseFindAccountDto from(Account account) {
		return new ResponseFindAccountDto(account.getUsername(), account.getRegdate());
	}

	public String getEmail() {
		return email;
	}

	public Date getRegdate() {
		return regdate;
	}

	@Override
	public String toString() {
		return "AccountFindResponseDto { email : "+ email + " regdate : " + regdate + " }";
	}
	
}
