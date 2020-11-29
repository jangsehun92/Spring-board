package jsh.project.board.article.domain;

import java.util.Date;

import jsh.project.board.article.dto.request.article.RequestArticleCreateDto;
import jsh.project.board.article.dto.request.article.RequestArticleUpdateDto;

public class Article {
	
	private int id;
	private int accountId;
	private String category;
	private int importance;
	private String title;
	private String content;
	private Date regdate;
	private Date modifyDate;
	
	private Article() {
		
	}
	
	private Article(RequestArticleCreateDto dto) {
		this.accountId = dto.getAccountId();
		this.category = dto.getCategory();
		this.importance = dto.getImportance();
		this.title = dto.getTitle();
		this.content = dto.getContent();
	}
	
<<<<<<< HEAD
	public Article(RequestArticleUpdateDto dto) {
		this.id = dto.getId();
		this.category = dto.getCategory();
		this.importance = dto.getImportance();
		this.title = dto.getTitle();
		this.content = dto.getContent();
	}
	
	public static Article from(Object obj) {
		if (obj instanceof RequestArticleCreateDto) {
			return new Article((RequestArticleCreateDto) obj);
		}
		if (obj instanceof RequestArticleUpdateDto) {
			return new Article((RequestArticleUpdateDto) obj);
		}
		return null;
=======
	public static Article from(RequestArticleCreateDto dto) {
		return new Article(dto);
>>>>>>> refactoring
	}
	
	public int getId() {
		return id;
	}

	public int getAccountId() {
		return accountId;
	}

	public String getCategory() {
		return category;
	}
	
	public int getImportance() {
		return importance;
	}
	
	public String getTitle() {
		return title;
	}

	public String getContent() {
		return content;
	}

	public Date getRegdate() {
		return regdate;
	}

	public Date getModifyDate() {
		return modifyDate;
	}
	
	public void editArticle(RequestArticleUpdateDto dto) {
		this.category = dto.getCategory();
		this.importance = dto.getImportance();
		this.title = dto.getTitle();
		this.content = dto.getContent();
		this.modifyDate = new Date();
	}
	
	@Override
	public String toString() {
		return "Article { id : "+id + " accountId : " + accountId 
					+ " category : " + category + " importance : " + importance 
					+ " title : " + title + " content : " + content + " regdate : " 
					+ regdate + " modifyDate : " + modifyDate + " }";
	}
	
}
