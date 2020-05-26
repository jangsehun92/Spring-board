package jsh.project.board.reply.dto;

public class Reply {
	
	private int id;
	private int articleId;
	private int accountId;
	private String content;
	private int enabled;
	
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
	
	interface toReply{
		Reply getReply();
	}
	
}