package jsh.project.board.reply.dto;

public class Reply {
	
	private int articleId;
	private int accountId;
	private String content;
	
	public Reply() {
		
	}
	
	public Reply(int articleId, int accountId, String content) {
		this.articleId = articleId;
		this.accountId = accountId;
		this.content = content;
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
	
}
