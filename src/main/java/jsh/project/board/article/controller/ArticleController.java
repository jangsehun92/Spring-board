package jsh.project.board.article.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import jsh.project.board.article.dto.Article;
import jsh.project.board.article.dto.ResponseArticles;
import jsh.project.board.article.service.ArticleService;

@Controller
public class ArticleController {
	
	private static final Logger logger = LoggerFactory.getLogger(ArticleController.class);
	
	private ArticleService articleService;
	
	public ArticleController(ArticleService articleService) {
		this.articleService = articleService;
	}
	
	@GetMapping("/articles")
	public @ResponseBody ResponseEntity<ResponseArticles> list(@RequestParam(required = false, defaultValue="1")int page){
		logger.info("GET /articles/"+page);
		return new ResponseEntity<ResponseArticles>(HttpStatus.OK);
	}
	
	
	@GetMapping("/article/{id}")
	public ResponseEntity<Article> article(@PathVariable("id") int id) {
		articleService.getArticle(id);
		return new ResponseEntity<>( HttpStatus.OK);
	}
	
	@PostMapping("/article")
	public ResponseEntity<HttpStatus> create(){
		return new ResponseEntity<HttpStatus>(HttpStatus.OK);
	}
	
	@PatchMapping("/article/{id}")
	public ResponseEntity<HttpStatus> create(@PathVariable("id") int id) {
		logger.info("PATCH /article/" + id);
		return new ResponseEntity<HttpStatus>(HttpStatus.OK);
	}

	// Article DELETE
	@DeleteMapping("/article/{id}")
	public ResponseEntity<HttpStatus> delete(@PathVariable("id") int id) {
		logger.info("DELETE /article/" + id);
		return new ResponseEntity<HttpStatus>(HttpStatus.OK);
	}

}
