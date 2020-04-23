package jsh.project.board.account.dto;

public class AccountPasswordResetRequestDto {
	private String email;
	private String name;
	private String birth;
	
	public AccountPasswordResetRequestDto() {
		
	}
	
	public AccountPasswordResetRequestDto(String email, String name, String birth) {
		this.email = email;
		this.name = name;
		this.birth = birth;
	}
	
	
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
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

	@Override
	public String toString() {
		return "AccountPasswordChangeDto { email : " + email +", name : " + name + " birth : " + birth +" } ";
	}
}
