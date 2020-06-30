package jsh.project.board.article.enums;

import jsh.project.board.global.enumModel.EnumModel;

public enum Importance implements EnumModel{
	일반(0),
	중요(1);
	
	private int importance;
	
	private Importance(int importance) {
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
