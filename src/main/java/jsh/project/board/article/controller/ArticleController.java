package jsh.project.board.article.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import jsh.project.board.article.service.ArticleService;

@Controller
public class ArticleController {
	
	private ArticleService articleService;
	
	public ArticleController(ArticleService articleService) {
		this.articleService = articleService;
	}
	
	@GetMapping("/articles")
	public String articles() {
		return "articles";
	}
	
	
	@GetMapping("/article/{id}")
	public String article(@PathVariable("id") int id) {
		articleService.getArticle(id);
		return "articles";
	}
	
}
