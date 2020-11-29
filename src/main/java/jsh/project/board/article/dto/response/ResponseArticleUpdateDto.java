package jsh.project.board.article.dto.response;

import jsh.project.board.article.domain.Article;

public class ResponseArticleUpdateDto {
	
	private int id;
	private int accountId;
	private String category;
	private int importance;
	private String title;
	private String content;
	
	private ResponseArticleUpdateDto(int id, int accountId, String category, int importance, String title, String content) {
		this.id = id;
		this.accountId = accountId;
		this.category = category;
		this.importance = importance;
		this.title = title;
		this.content = content;
	}
	
	public static ResponseArticleUpdateDto from(Article article) {
		return new ResponseArticleUpdateDto(article.getId(), 
											article.getAccountId(), 
											article.getCategory(), 
											article.getImportance(), 
											article.getTitle(), 
											article.getContent());
	}

	public int getId() {
		return id;
	}

	public int getAccountId() {
		return accountId;
	}

	public String getCategory() {
		return category;
	}
	
	public int getImportance() {
		return importance;
	}
	
	public String getTitle() {
		return title;
	}

	public String getContent() {
		return content;
	}

	@Override
	public String toString() {
		return "ResponseArticleUpdateDto { id : " + id + " accountId : " + accountId 
					+ " category : " + category + " importance : " + importance 
					+ " title : " + title + " content : " + content + " }";
	}
	

}
