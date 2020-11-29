package jsh.project.board.article.dto.request.article;

public class RequestArticlesDto {
	private int page;
	private int accountId;
	private String category;
	private String query;
	private String sort;
	private int startCount;
	private int endCount;
	
	public RequestArticlesDto() {
		this.page = 1;
		this.category = "";
		this.query = "";
		this.sort = "id";
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getAccountId() {
		return accountId;
	}

	public void setAccountId(int accountId) {
		this.accountId = accountId;
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
		this.query = query.trim();
	}

	public String getSort() {
		return sort;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}

	public int getStartCount() {
		return startCount;
	}

	public void setStartCount(int startCount) {
		this.startCount = startCount;
	}

	public int getEndCount() {
		return endCount;
	}

	public void setEndCount(int endCount) {
		this.endCount = endCount;
	}
	
	@Override
	public String toString() {
		return "requestArticlesDto {page : " + page + " accountId : " + accountId +" category : " + category + " query : " + query + " sort : " + sort + "}";
	}
	
	
}
