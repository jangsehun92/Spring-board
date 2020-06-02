package jsh.project.board.article.enums;

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
