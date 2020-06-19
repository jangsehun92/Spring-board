package jsh.project.board.article.enums;

import jsh.project.board.global.enumModel.EnumModel;

public enum ArticleImportance implements EnumModel{
	NOMAL(0),
	IMPORTANCE(1);
	
	private int importance;
	
	private ArticleImportance(int importance) {
		this.importance = importance;
	}

	@Override
	public String getKey() {
		return name();
	}

	@Override
	public String getValue() {
		return Integer.toString(importance);
	}
	
	

}
