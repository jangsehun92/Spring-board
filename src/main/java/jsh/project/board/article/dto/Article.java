package jsh.project.board.article.dto;

import java.sql.Date;

public class Article {
	
	private int id;
	private int accountId;
	private String title;
	private String content;
	private Date regdate;
	
	public Article() {
		
	}
	
	public Article(int id, int accountId, String title, String content, Date regdate) {
		this.id = id;
		this.accountId = accountId;
		this.title = title;
		this.content = content;
		this.regdate = regdate;
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

	
}
