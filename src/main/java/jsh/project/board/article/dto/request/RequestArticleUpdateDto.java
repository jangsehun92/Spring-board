package jsh.project.board.article.dto.request;

import java.util.Date;

import jsh.project.board.article.domain.Article;
import jsh.project.board.article.domain.Article.getArticle;
import jsh.project.board.article.enums.AllCategory;

public class RequestArticleUpdateDto implements getArticle{
	
	private int id;
	private int accountId;
	private String category;
	private int importance;
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
		this.content = content;
	}
	
	@Override
	public Article toArticle() {
		final Article article = new Article();
		article.setId(this.id);
		article.setCategory(AllCategory.valueOf(this.category.toUpperCase()).getValue());
		article.setImportance(this.importance);
		article.setTitle(this.title);
		article.setContent(this.content);
		article.setModifyDate(new Date());
		return article;
	}
	
	
}
