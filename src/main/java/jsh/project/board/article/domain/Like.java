package jsh.project.board.article.domain;

import jsh.project.board.article.dto.request.like.RequestLikeDto;

public class Like {
	
	private int articleId;
	private int accountId;
	
	private Like(int articleId, int accountId) {
		this.articleId = articleId;
		this.accountId = accountId;
	}
	
	public static Like of(int articleId, int accountId) {
		return new Like(articleId, accountId);
	}
	
	public static Like from(RequestLikeDto dto) {
		return new Like(dto.getArticleId(), dto.getAccountId());
	}
	
	public int getArticleId() {
		return articleId;
	}
	
	public int getAccountId() {
		return accountId;
	}

}
