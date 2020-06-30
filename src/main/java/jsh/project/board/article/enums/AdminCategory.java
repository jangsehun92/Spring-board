package jsh.project.board.article.enums;

import jsh.project.board.global.enumModel.EnumModel;

public enum AdminCategory implements EnumModel{
	
	NOTICE("공지사항");
	
	private final String category;
	
	AdminCategory(String category){
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
