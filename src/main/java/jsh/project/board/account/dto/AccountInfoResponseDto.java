package jsh.project.board.account.dto;

import java.util.List;

import jsh.project.board.article.dto.Article;

public class AccountInfoResponseDto {
	
	private AccountResponseDto accountResponseDto;
	private List<Article> articles;
	
	public AccountInfoResponseDto(AccountResponseDto accountResponseDto, List<Article> articles) {
		this.accountResponseDto = accountResponseDto;
		this.articles = articles;
	}

	public AccountResponseDto getAccountResponseDto() {
		return accountResponseDto;
	}

	public void setAccountResponseDto(AccountResponseDto accountResponseDto) {
		this.accountResponseDto = accountResponseDto;
	}

	public List<Article> getArticles() {
		return articles;
	}

	public void setArticles(List<Article> articles) {
		this.articles = articles;
	}
	
	

}
