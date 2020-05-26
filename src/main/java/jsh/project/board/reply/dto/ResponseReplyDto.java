package jsh.project.board.reply.dto;

import java.sql.Date;

public class ResponseReplyDto {
	
	private int id;
	private int articleId;
	private int accountId;
	private String nickname;
	private String content;
	private Date regdate;
	private Date modifyDate;
	private boolean enabled;
	
	public ResponseReplyDto() {
		
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

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
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
	
	public boolean getEnabled() {
		return enabled;
	}
	
	public void setEnabled(int enabled) {
		if(enabled == 0) {
			this.content = "삭제된 댓글입니다.";
			this.enabled = false;
		}else {
			this.enabled = true;
		}
	}
	
}