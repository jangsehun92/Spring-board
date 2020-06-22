package jsh.project.board.reply.domain;

import java.util.Date;

public class Reply {
	
	private int id;
	private int articleId;
	private int accountId;
	private String content;
	private Date regdate;
	private Date modifyDate;
	private int enabled;
	
	public Reply(int articleId) {
		this.articleId = articleId;
	}
	public Reply(int articleId, int accountId) {
		this.articleId = articleId;
		this.accountId = accountId;
	}
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}

	public int getArticleId() {
		return articleId;
	}

	public void setArticleId(int articleId) {
		this.articleId = articleId;
	}

	public int getAccountId() {
		return accountId;
	}

	public void setAccountId(int accountId) {
		this.accountId = accountId;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	
	public int getEnabled() {
		return enabled;
	}
	
	public void setEnabled(int enabled) {
		this.enabled= enabled;
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
	
	public interface ReplyConverter{
		Reply toReply();
	}
	
}
