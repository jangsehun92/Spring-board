package jsh.project.board.account.dto.response;

public class ResponseAccountDto {
	
	private int id;
	private String nickname;
	
	public ResponseAccountDto() {
		
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