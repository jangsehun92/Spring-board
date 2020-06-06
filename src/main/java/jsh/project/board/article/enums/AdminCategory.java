package jsh.project.board.article.enums;

public enum AdminCategory implements EnumModel{
	
	NOTICE("notice");
	
	private String category;
	
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
