package jsh.project.board.article.dto;

import jsh.project.board.article.dto.like.RequestLikeDto;

public class RequestArticleDeleteDto {
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
	
	public RequestLikeDto getLikeDto() {
		RequestLikeDto dto = new RequestLikeDto();
		dto.setArticleId(this.articleId);
		dto.setAccountId(this.accountId);
		return dto;
	}
	
	

}
