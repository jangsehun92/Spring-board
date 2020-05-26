package jsh.project.board.article.dto;

import jsh.project.board.article.domain.Article;

public class RequestArticleUpdateDto {
	
	private int id;
	private int accountId;
	private String title;
	private String content;
	
	public RequestArticleUpdateDto() {
		
	}
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
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
		article.setId(this.id);
		article.setAccountId(this.accountId);
		article.setTitle(this.title);
		article.setContent(this.content);
		return article;
	}
	
	
}
