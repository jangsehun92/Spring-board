package jsh.project.board.account.enums;

import jsh.project.board.global.enumModel.EnumModel;

public enum AuthOption implements EnumModel{
	
	SIGNUP("signup"),
	RESET("reset");
	
	private final String option;
	
	AuthOption(String option) {
		this.option = option;
	}
	
	@Override
	public String getKey() {
		return name();
	}

	@Override
	public String getValue() {
		return option;
	}

}
