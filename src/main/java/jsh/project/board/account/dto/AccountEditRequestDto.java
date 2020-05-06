package jsh.project.board.account.dto;

import javax.validation.constraints.NotBlank;

public class AccountEditRequestDto {
	@NotBlank(message = "닉네임을 입력해주세요.")
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
