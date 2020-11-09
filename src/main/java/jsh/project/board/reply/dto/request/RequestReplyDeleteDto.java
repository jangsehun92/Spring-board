package jsh.project.board.reply.dto.request;

import jsh.project.board.reply.domain.Reply;
import jsh.project.board.reply.domain.Reply.ReplyConverter;

public class RequestReplyDeleteDto implements ReplyConverter{
	
	private int id;
	private int articleId;
	private int accountId;
	
	public RequestReplyDeleteDto() {
		
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
	
	@Override
	public String toString() {
		return "RequestReplyDeleleteDto {id : " + id + " articleId : " + articleId + " accountId : " + accountId +" }";
	}

	@Override
	public Reply toReply() {
		final Reply reply = new Reply(this);
		return reply;
	}
	
	

}
