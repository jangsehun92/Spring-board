package jsh.project.board.article.dto.request.like;

public class RequestLikeDto {
	
	private int articleId;
	private int accountId;
	
	public RequestLikeDto() {
		
	}
	
	public RequestLikeDto(int articleId, int accountId) {
		this.articleId = articleId;
		this.accountId = accountId;
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
		return "RequestLikeDto { articleId : " + articleId + " accountId : " + accountId + " }";
	}
	
	public interface LikeDtoConverter{
		RequestLikeDto toLikeDto();
	}

}
