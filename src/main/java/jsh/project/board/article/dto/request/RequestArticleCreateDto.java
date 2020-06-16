package jsh.project.board.article.dto.request;

import java.util.Date;

import javax.validation.constraints.NotBlank;

import jsh.project.board.article.domain.Article;
import jsh.project.board.article.domain.Article.getArticle;

public class RequestArticleCreateDto implements getArticle{
	
	private int accountId;
	private String category;
	private int importance;
	@NotBlank(message = "제목을 입력해주세요.")
	private String title;
	@NotBlank(message = "내용을 입력해주세요.")
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
	
	public int getImportance() {
		return importance;
	}
	
	public void setImportance(int importance) {
		this.importance = importance;
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
		this.content = content.trim();
	}
	
	@Override
	public Article toArticle() {
		final Article article = new Article();
		article.setAccountId(this.accountId);
		article.setCategory(this.category);
		article.setImportance(this.importance);
		article.setTitle(this.title);
		article.setContent(this.content);
		article.setRegdate(new Date());
		return article;
	}

}
