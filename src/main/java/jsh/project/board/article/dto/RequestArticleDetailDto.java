package jsh.project.board.article.dto;

import jsh.project.board.article.dto.like.RequestLikeDto;

public class RequestArticleDetailDto {
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
	
	public RequestLikeDto getLikeDto() {
		RequestLikeDto dto = new RequestLikeDto();
		dto.setArticleId(this.id);
		dto.setAccountId(this.accountId);
		return dto;
	}
	
	@Override
	public String toString() {
		return "RequestArticleDetailDto { id : " + id + " accountId : "+ accountId + " }";
	}
	
}
