package jsh.project.board.reply.dto;

public class RequestReplyDto {
	
	//@유효성 검사를 해야한다.
	
	private int articleId;
	private int accountId;
	private String content;
	
	public RequestReplyDto() {
		
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
	
	public Reply toReply() {
		Reply reply = new Reply(this.articleId, this.accountId, this.content);
		return reply;
	}

}
