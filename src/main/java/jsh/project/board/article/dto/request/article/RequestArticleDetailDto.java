package jsh.project.board.article.dto.request.article;

import jsh.project.board.article.dto.request.like.RequestLikeDto;
import jsh.project.board.article.dto.request.like.RequestLikeDto.LikeDtoConverter;

public class RequestArticleDetailDto implements LikeDtoConverter {
	private int id;
	private int accountId;
	
	public RequestArticleDetailDto() {
		
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getAccountId() {
		return accountId;
	}

	public void setAccountId(int accountId) {
		this.accountId = accountId;
	}
	
	@Override
	public RequestLikeDto toLikeDto() {
		RequestLikeDto dto = new RequestLikeDto(id, accountId);
		return dto;
	}
	
	@Override
	public String toString() {
		return "RequestArticleDetailDto { id : " + id + " accountId : "+ accountId + " }";
	}
	
}
