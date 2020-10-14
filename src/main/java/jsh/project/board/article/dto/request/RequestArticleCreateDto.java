package jsh.project.board.article.dto.request;

import javax.validation.constraints.NotBlank;

import jsh.project.board.article.domain.Article;
import jsh.project.board.article.domain.Article.ArticleConverter;
import jsh.project.board.article.enums.AllCategory;
import jsh.project.board.article.enums.Importance;

public class RequestArticleCreateDto implements ArticleConverter{
	
	private int accountId;
	private String category;
	private int importance;
	@NotBlank(message = "제목을 입력해주세요.")
	private String title;
	@NotBlank(message = "내용을 입력해주세요.")
	private String content;
	
	public RequestArticleCreateDto() {
		
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
		this.category = AllCategory.valueOf(category.toUpperCase()).getValue();
	}
	
	public int getImportance() {
		return importance;
	}
	
	public void setImportance(String importance) {
		this.importance = Integer.parseInt(Importance.valueOf(importance.toUpperCase()).getValue());
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
	public Article toArticle() {
		final Article article = new Article(this);
		return article;
	}
	
	@Override
	public String toString() {
		return "RequestArticleCreateDto { accountId : "+accountId + " category : " + category + " importance : " + importance + " content : " + content + " }";
	}

}
