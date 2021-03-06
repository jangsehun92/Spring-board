package jsh.project.board.reply.dto.request;

import javax.validation.constraints.NotBlank;

public class RequestReplyCreateDto {
	
	private int articleId;
	private int accountId;
	private int replyGroup;
	private int replyGroupOrder;
	private int replyDepth;
	@NotBlank(message = "댓글을 입력해 주세요.")
	private String content;
	
	public RequestReplyCreateDto() {
		
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
	
	public int getReplyGroup() {
		return replyGroup;
	}
	
	public void setReplyGroup(int replyGroup) {
		this.replyGroup = replyGroup;
	}
	
	public int getReplyGroupOrder() {
		return replyGroupOrder;
	}

	public void setReplyGroupOrder(int replyGroupOrder) {
		this.replyGroupOrder = replyGroupOrder;
	}

	public int getReplyDepth() {
		return replyDepth;
	}

	public void setReplyDepth(int replyDepth) {
		this.replyDepth = replyDepth;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content.trim();
	}
	
	@Override
	public String toString() {
		return "RequestReplyCreateDto { articleId : " + articleId 
				+ " accountId : " + accountId 
				+ " replyGroup : " + replyGroup
				+ " replyGroupOrder : " + replyGroupOrder
				+ " replyDepth : " + replyDepth
				+ " content : " + content + " }";
	}
	
}
