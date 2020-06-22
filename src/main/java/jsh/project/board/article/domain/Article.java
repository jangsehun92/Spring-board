package jsh.project.board.article.domain;

import java.util.Date;

import jsh.project.board.article.dto.request.like.RequestLikeDto;

public class Article {
	
	private int id;
	private int accountId;
	private String category;
	private int importance;
	private String title;
	private String content;
	private Date regdate;
	private Date modifyDate;
	
	public Article() {
		
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
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Date getRegdate() {
		return regdate;
	}

	public void setRegdate(Date regdate) {
		this.regdate = regdate;
	}
	
	public Date getModifyDate() {
		return modifyDate;
	}
	
	public void setModifyDate(Date modifyDate) {
		this.modifyDate = modifyDate;
	}
	
	@Override
	public String toString() {
		return "Article {id : "+id + " accountId : " + accountId + " category : " + category + " importance : " + importance + " title : " + title + " content : " + content + " regdate : " + regdate + " modifyDate : " + modifyDate;
	}
	
	public interface ArticleConverter{
		Article toArticle();
	}
	
	public interface LikeDtoConverter{
		RequestLikeDto toLikeDto();
	}

	
}
