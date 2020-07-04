package jsh.project.board.article.dto.request;

import jsh.project.board.article.dto.request.like.RequestLikeDto;
import jsh.project.board.article.dto.request.like.RequestLikeDto.LikeDtoConverter;

public class RequestArticleDeleteDto implements LikeDtoConverter{
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
