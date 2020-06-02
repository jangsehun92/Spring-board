package jsh.project.board.article.enums;

public enum AllCategory{
	
	NOTICE("notice"),
	COMMUNITY("community"),
	QUESTIONS("questions");
	
	private final String category;
	
	private AllCategory(String category) {
		this.category = category;
	}
	
	public String getCategory(){
		return category;
	}

}


