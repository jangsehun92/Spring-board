package jsh.project.board.article.enums.dto;

import jsh.project.board.article.enums.AdminCategory;
import jsh.project.board.article.enums.UserCategory;
import jsh.project.board.global.enumModel.EnumModel;

public class CategoryDto implements EnumModel{
	
	private String key;
	private String value;
	
	public CategoryDto(AdminCategory adminCategory) {
		this.key = adminCategory.getKey();
		this.value = adminCategory.getValue();
	}
	
	public CategoryDto(UserCategory userCategory) {
		this.key = userCategory.getKey();
		this.value = userCategory.getValue();
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
