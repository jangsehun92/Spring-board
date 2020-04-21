package jsh.project.board.account.dto;

public class AccountCheckDto {
	
	private String email;
	private int enabled;
	
	public AccountCheckDto() {
		
	}
	
	public AccountCheckDto(String email, int enabled) {
		this.email = email;
		this.enabled = enabled;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public int getEnalbed() {
		return enabled;
	}

	public void setEnabled(int locked) {
		this.enabled = locked;
	}
	
	public boolean isEnabled() {
		if(enabled == 0) {
			return false;
		}
		return true;
	}
}
