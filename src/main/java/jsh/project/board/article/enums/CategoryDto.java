package jsh.project.board.article.enums;

public class CategoryDto {
	
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
	
	public String getKey() {
		return key;
	}
	
	public String getValue() {
		return value;
	}
	
	

}
