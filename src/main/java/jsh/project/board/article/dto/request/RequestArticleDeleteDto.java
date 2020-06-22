package jsh.project.board.article.dto.request;

import jsh.project.board.article.domain.Article.LikeDtoTransform;
import jsh.project.board.article.dto.request.like.RequestLikeDto;

public class RequestArticleDeleteDto implements LikeDtoTransform{
	private int articleId;
	private int accountId;
	
	public RequestArticleDeleteDto() {
		
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
	public RequestLikeDto toLikeDto() {
		RequestLikeDto dto = new RequestLikeDto(articleId, accountId);
		return dto;
	}
	
	

}
