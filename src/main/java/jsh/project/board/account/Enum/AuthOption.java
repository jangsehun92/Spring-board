package jsh.project.board.account.Enum;

public enum AuthOption {
	
	SIGNUP("signup"),
	LOCKED("lock"),
	RESET("reset");
	
	private final String option;
	
	AuthOption(String option) {
		this.option = option;
	}
	
	public String getOption() {
		return option;
	}

}
