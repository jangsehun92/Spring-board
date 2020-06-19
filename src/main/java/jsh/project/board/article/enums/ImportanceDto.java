package jsh.project.board.article.enums;

public class ImportanceDto {
	
	private String key;
	private String value;
	
	public ImportanceDto(ArticleImportance articleImportance) {
		this.key = articleImportance.getKey();
		this.value = articleImportance.getValue();
	}
	
	public String getKey() {
		return key;
	}
	
	public String getValue() {
		return value;
	}

}
