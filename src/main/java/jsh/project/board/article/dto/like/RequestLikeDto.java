package jsh.project.board.article.dto.like;

public class RequestLikeDto {
	
	private int articleId;
	private int accountId;
	
	public RequestLikeDto() {
		
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
	

}
