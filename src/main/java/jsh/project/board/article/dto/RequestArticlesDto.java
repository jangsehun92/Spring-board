package jsh.project.board.article.dto;

public class RequestArticlesDto {
	private int page;
	private String category;
	private String query;
	private String sort;
	
	public RequestArticlesDto() {
		
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
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
	
	@Override
	public String toString() {
		return "requestArticlesDto {page : " + page + " category : " + category + " query : " + query + " sort : " + sort + "}";
	}
	
	
}
