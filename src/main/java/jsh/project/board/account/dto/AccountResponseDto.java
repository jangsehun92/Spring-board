package jsh.project.board.account.dto;

public class AccountResponseDto {
	
	private int id;
	private String nickname;
	
	public AccountResponseDto() {
		
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

}
