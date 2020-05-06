package jsh.project.board.account.dto;

import javax.validation.constraints.NotBlank;

public class AccountFindRequestDto {
	
	@NotBlank(message = "이름을 입력해주세요.")
	private String name;
	@NotBlank(message = "생년월일을 입력해주세요.")
	private String birth;
	
	public AccountFindRequestDto() {
		
	}
	
	public AccountFindRequestDto(String name, String birth) {
		this.name = name;
		this.birth = birth;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getBirth() {
		return birth;
	}

	public void setBirth(String birth) {
		this.birth = birth;
	}
	

}
