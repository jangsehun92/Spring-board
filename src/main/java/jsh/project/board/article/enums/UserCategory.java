package jsh.project.board.article.enums;

import jsh.project.board.global.enumModel.EnumModel;

public enum UserCategory implements EnumModel{
	
	COMMUNITY("community"),
	QUESTIONS("questions");
	
	private String category;
	
	UserCategory(String category) {
		this.category = category;
	}

	@Override
	public String getKey() {
		return name();
	}

	@Override
	public String getValue() {
		return category;
	}
	

}
