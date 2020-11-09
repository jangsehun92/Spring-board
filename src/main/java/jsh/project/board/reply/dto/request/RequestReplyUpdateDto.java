package jsh.project.board.reply.dto.request;

import javax.validation.constraints.NotBlank;

import jsh.project.board.reply.domain.Reply;
import jsh.project.board.reply.domain.Reply.ReplyConverter;

public class RequestReplyUpdateDto implements ReplyConverter{
	
	private int id;
	private int articleId;
	private int accountId;
	@NotBlank(message = "댓글을 입력해 주세요.")
	private String content;
	
	public RequestReplyUpdateDto() {
		
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
		this.content = content.trim();
	}
	
	@Override
	public String toString() {
		return "RequestReplyUpdateDto {id : " + id + " articleId : " + articleId + " accountId : " + accountId + " content : " + content + " }";
	}
	
	@Override
	public Reply toReply() {
		final Reply reply = new Reply(this);
		return reply;
	}

}
