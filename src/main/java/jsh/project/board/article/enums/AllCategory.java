package jsh.project.board.article.enums;

import jsh.project.board.global.enumModel.EnumModel;

public enum AllCategory implements EnumModel{
	
	NOTICE("notice"),
	COMMUNITY("community"),
	QUESTIONS("questions");
	
	private final String category;
	
	private AllCategory(String category) {
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


