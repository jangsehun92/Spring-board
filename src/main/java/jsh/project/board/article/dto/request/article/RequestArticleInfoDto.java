package jsh.project.board.article.dto.request.article;

public class RequestArticleInfoDto {
	
	private int id;
	private int accountId;
	
	public RequestArticleInfoDto() {
		
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
	
	@Override
	public String toString() {
		return "RequestArticleInfoDto { id : " + id + " accountId : " + accountId + " }";
	}
	
	

}
