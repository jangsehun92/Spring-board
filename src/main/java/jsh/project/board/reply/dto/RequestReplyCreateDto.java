package jsh.project.board.reply.dto;

import javax.validation.constraints.NotBlank;

import jsh.project.board.reply.dto.Reply.toReply;

public class RequestReplyCreateDto implements toReply{
	
	private int articleId;
	private int accountId;
	@NotBlank(message = "내용을 입력해 주세요.")
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

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content.trim();
	}
	
	@Override
	public String toString() {
		return "RequestReplyCreateDto { articleId : " + articleId + " accountId : " + accountId + " content : " + content + " }";
	}
	
	@Override
	public Reply getReply() {
		final Reply reply = new Reply(this.articleId, this.accountId);
		reply.setContent(this.content);
		reply.setEnabled(1);
		return reply;
	}

}
