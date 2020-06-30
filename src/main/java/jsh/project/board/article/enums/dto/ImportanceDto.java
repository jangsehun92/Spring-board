package jsh.project.board.article.enums.dto;

import jsh.project.board.article.enums.Importance;
import jsh.project.board.global.enumModel.EnumModel;

public class ImportanceDto implements EnumModel{
	
	private String key;
	private String value;
	
	public ImportanceDto(Importance importance) {
		this.key = importance.getKey();
		this.value = importance.getValue();
	}
	
	@Override
	public String getKey() {
		return key;
	}
	
	@Override
	public String getValue() {
		return value;
	}

}
