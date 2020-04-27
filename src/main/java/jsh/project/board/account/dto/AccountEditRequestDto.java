package jsh.project.board.account.dto;

public class AccountEditRequestDto {
	private String nickname;
	
	public AccountEditRequestDto() {
		
	}
	
	public AccountEditRequestDto(String nickname) {
		this.nickname = nickname;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	

}
