package jsh.project.board.article.dto.response;

import java.util.List;

import jsh.project.board.article.dto.request.article.RequestArticlesDto;
import jsh.project.board.global.infra.util.Pagination;

public class ResponseBoardDto {
	private final List<ResponseArticleDto> articles;
	private final Pagination pagination;
	private final String category;
	private final String query;
	private final String sort;
	
	private ResponseBoardDto(List<ResponseArticleDto> articles, Pagination pagination, RequestArticlesDto dto) {
		this.articles = articles;
		this.pagination = pagination;
		this.category = dto.getCategory().toLowerCase();
		this.query = dto.getQuery();
		this.sort = dto.getSort();
	}
	
	public static ResponseBoardDto of(List<ResponseArticleDto> articles, Pagination pagination, RequestArticlesDto dto) {
		return new ResponseBoardDto(articles, pagination, dto);
	}
	
	public List<ResponseArticleDto> getArticles() {
		return articles;
	}

	public Pagination getPagination() {
		return pagination;
	}

	public String getCategory() {
		return category;
	}

	public String getQuery() {
		return query;
	}

	public String getSort() {
		return sort;
	}
	
}
