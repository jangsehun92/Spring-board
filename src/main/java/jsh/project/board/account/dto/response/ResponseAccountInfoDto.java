package jsh.project.board.account.dto.response;

import jsh.project.board.account.domain.Account;

public class ResponseAccountInfoDto {
	
	private int id;
	private String nickname;
	
	private ResponseAccountInfoDto(int id, String nickname) {
		this.id = id;
		this.nickname = nickname;
	}
	
	public static ResponseAccountInfoDto from(Account account) {
		ResponseAccountInfoDto dto = new ResponseAccountInfoDto(account.getId(), account.getNickname());
		return dto;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	
	@Override
	public String toString() {
		return "ResponseAccountInfoDto { id : " + id + " nickname : " + nickname + " }";
	}

}
