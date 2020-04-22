package jsh.project.board.account.dto;

public class AccountFindDto {
	
	private String name;
	private String birth;
	
	public AccountFindDto() {
		
	}
	
	public AccountFindDto(String name, String birth) {
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
