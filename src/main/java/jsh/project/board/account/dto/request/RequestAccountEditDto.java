package jsh.project.board.account.dto.request;

import javax.validation.constraints.NotBlank;

public class RequestAccountEditDto {
	@NotBlank(message = "닉네임을 입력해주세요.")
	private String nickname;
	
	public RequestAccountEditDto() {
		
	}
	
	public RequestAccountEditDto(String nickname) {
		this.nickname = nickname;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	

}
