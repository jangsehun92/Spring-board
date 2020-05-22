package jsh.project.board.article.dto;

import jsh.project.board.article.domain.Article;

public class RequestArticleCreateDto {
	
	private int accountId;
	private String category;
	private String title;
	private String content;
	
	public RequestArticleCreateDto() {
		
	}
	
	public int getAccountId() {
		return accountId;
	}
	
	public void setAccountId(int accountId) {
		this.accountId = accountId;
	}
	
	public String getCategory() {
		return category;
	}
	
	public void setCategory(String category) {
		this.category = category;
	}
	
	public String getTitle() {
		return title;
	}
	
	public void setTitle(String title) {
		this.title = title.trim();
	}
	
	public String getContent() {
		return content;
	}
	
	public void setContent(String content) {
		this.content = content;
	}
	
	public Article toArticle() {
		Article article = new Article();
		article.setAccountId(this.accountId);
		article.setCategory(category);
		article.setTitle(this.title);
		article.setContent(this.content);
		return article;
	}

}
