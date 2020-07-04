package jsh.project.board.article.dto.response;

public class ResponseArticleUpdateDto {
	
	private int id;
	private int accountId;
	private String category;
	private int importance;
	private String title;
	private String content;
	
	public ResponseArticleUpdateDto() {
		
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
	
	public String getCategory() {
		return category;
	}
	
	public void setCategory(String category) {
		this.category = category;
	}
	
	public int getImportance() {
		return importance;
	}
	
	public void setImportance(int importance) {
		this.importance = importance;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	
	@Override
	public String toString() {
		return "ResponseArticleUpdateDto { id : " + id + " accountId : " + accountId 
					+ " category : " + category + " importance : " + importance 
					+ " title : " + title + " content : " + content + " }";
	}
	

}
