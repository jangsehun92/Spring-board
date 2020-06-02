package jsh.project.board.article.dto.request;

import java.util.Date;

import javax.validation.constraints.NotBlank;

import jsh.project.board.article.domain.Article;
import jsh.project.board.article.domain.Article.getArticle;
import jsh.project.board.article.enums.AllCategory;

public class RequestArticleCreateDto implements getArticle{
	
	private int accountId;
	private String category;
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
		this.category = category;
	}
	
	public String getTitle() {
		return title;
	}
	
	public void setTitle(String title) {
		this.title = title.trim();
	}
	
	public String getContent() {
		return content;
	}
	
	public void setContent(String content) {
		this.content = content.trim();
	}
	
	@Override
	public Article toArticle() {
		Article article = new Article();
		article.setAccountId(this.accountId);
		article.setCategory(AllCategory.valueOf(this.category).getCategory());
		if(category.equals("notice")) {
			article.setImportance(1);
		}
		article.setTitle(this.title);
		article.setContent(this.content);
		article.setRegdate(new Date());
		return article;
	}

}