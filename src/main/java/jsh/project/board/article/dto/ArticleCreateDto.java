package jsh.project.board.article.dto;

public class ArticleCreateDto {
	
	private int accountId;
	private String title;
	private String content;
	
	public ArticleCreateDto() {
		
	}
	
	public ArticleCreateDto(int accountId, String title, String content) {
		this.accountId = accountId;
		this.title = title;
		this.content = content;
	}
	
	public int getAccountId() {
		return accountId;
	}
	
	public void setAccountId(int accountId) {
		this.accountId = accountId;
	}
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}

}
