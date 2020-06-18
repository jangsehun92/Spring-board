package jsh.project.board.account.enums;

import jsh.project.board.global.enumModel.EnumModel;

public enum Role implements EnumModel{
	
	USER("ROLE_USER"),
	ADMIN("ROLE_ADMIN");
	
	private String role;
	
	Role(String role) {
		this.role = role;
	}

	@Override
	public String getKey() {
		return name();
	}

	@Override
	public String getValue() {
		return role;
	}

}
