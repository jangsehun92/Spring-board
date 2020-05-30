package jsh.project.board.article.dto.response;

import java.util.List;

import jsh.project.board.global.infra.util.Pagination;

public class ResponseBoardDto {
	private List<ResponseArticleDto> articles;
	private Pagination pagination;
	private String category;
	private String query;
	private String sort;
	
	public ResponseBoardDto() {
		
	}
	
	public List<ResponseArticleDto> getArticles() {
		return articles;
	}

	public void setArticles(List<ResponseArticleDto> articles) {
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
	
	public String getSort() {
		return sort;
	}
	
	public void setSort(String sort) {
		this.sort = sort;
	}
	
	
	
}
