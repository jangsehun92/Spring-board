package jsh.project.board.article.dto;

import java.sql.Date;

public class ResponseArticleDetailDto {
	private int id;
	private int accountId;
	private String category;
	private String nickname;
	private String title;
	private String content;
	private int viewCount;
	private int replyCount;
	private int likeCount;
	private Date regdate;
	private boolean likeCheck;
	
	public ResponseArticleDetailDto() {
		
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

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
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

	public int getViewCount() {
		return viewCount;
	}

	public void setViewCount(int viewCount) {
		this.viewCount = viewCount;
	}

	public int getReplyCount() {
		return replyCount;
	}

	public void setReplyCount(int replyCount) {
		this.replyCount = replyCount;
	}

	public int getLikeCount() {
		return likeCount;
	}

	public void setLikeCount(int likeCount) {
		this.likeCount = likeCount;
	}

	public Date getRegdate() {
		return regdate;
	}

	public void setRegdate(Date regdate) {
		this.regdate = regdate;
	}
	
	public boolean getLikeCheck() {
		return likeCheck;
	}
	
	public void setLikeCheck(int likeCheck) {
		if(likeCheck == 0) {
			this.likeCheck = false;
		}else {
			this.likeCheck = true;
		}
	}
	
}
