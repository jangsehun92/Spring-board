package jsh.project.board.article.dto.response;

import java.util.Date;

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
	private Date modifyDate;
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
	
	public Date getModifyDate() {
		return modifyDate;
	}
	
	public void setModifyDate(Date modifyDate) {
		this.modifyDate = modifyDate;
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
	
	@Override
	public String toString() {
		return "ResponseArticleDetailDto { id : " + id + " accountId : " + accountId + " category : " + category
				+ " nickname : " + nickname + " title : " + title + " content : " + content + " viewCount : " + viewCount + " replyCount : " + replyCount
				+ " likeCount : " + likeCount + " regdate : " + regdate + " modifyDate : " + modifyDate + " likeCheck : " + likeCheck + " }";
	}
	
}
