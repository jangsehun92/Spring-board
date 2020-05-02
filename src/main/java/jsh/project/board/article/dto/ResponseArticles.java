package jsh.project.board.article.dto;

import java.util.List;

import jsh.project.board.global.infra.util.Pagination;

public class ResponseArticles {
	private List<Article> articles;
	private Pagination pagination;
	private String category;
	private String query;
	
	public ResponseArticles() {
		
	}
	
	public List<Article> getArticles() {
		return articles;
	}

	public void setArticles(List<Article> articles) {
		this.articles = articles;
	}

	public Pagination getPagination() {
		return pagination;
	}

	public void setPagination(Pagination pagination) {
		this.pagination = pagination;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getQuery() {
		return query;
	}

	public void setQuery(String query) {
		this.query = query;
	}
	
}
