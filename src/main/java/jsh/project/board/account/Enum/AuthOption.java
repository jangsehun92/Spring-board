package jsh.project.board.account.Enum;

public enum AuthOption {
	
	SIGNUP("signup"),
	LOCKED("lock");
	
	private final String option;
	
	AuthOption(String option) {
		this.option = option;
	}
	
	public String getOption() {
		return option;
	}

}
