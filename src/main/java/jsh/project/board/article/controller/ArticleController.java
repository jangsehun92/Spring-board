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
import jsh.project.board.article.dto.ArticleCreateDto;
import jsh.project.board.article.dto.RequestArticlesDto;
import jsh.project.board.article.dto.ResponseArticlesDto;
import jsh.project.board.article.service.ArticleService;

@Controller
public class ArticleController {
	
	private static final Logger logger = LoggerFactory.getLogger(ArticleController.class);
	
	private ArticleService articleService;
	
	public ArticleController(ArticleService articleService) {
		this.articleService = articleService;
	}
	
	// All Aritcles 
	@GetMapping("/articles")
	public @ResponseBody ResponseEntity<ResponseArticlesDto> articleList(RequestArticlesDto dto){
			//@RequestParam(required = false, defaultValue="1")int page){
		logger.info("GET /articles/"+dto.getPage());
		logger.info(dto.toString());
		ResponseArticlesDto responseArticles = articleService.getArticles(dto.getPage());
		return new ResponseEntity<ResponseArticlesDto>(responseArticles, HttpStatus.OK);
	}
	
	// category Aritcles 
	@GetMapping("/articles/{category}")
	public @ResponseBody ResponseEntity<ResponseArticlesDto> articleListByCategory(@PathVariable String category, @RequestParam(required = false, defaultValue="1")int page){
		logger.info("GET /articles/"+category+"?page="+page);
		return new ResponseEntity<ResponseArticlesDto>(HttpStatus.OK);
	}
		
	// 해당 유저의 Aritcles
	@GetMapping("/articles/{id}")
	public @ResponseBody ResponseEntity<ResponseArticlesDto> articleListByAccount(@PathVariable int id, @RequestParam(required = false, defaultValue="1")int page){
		logger.info("GET /articles/"+id+"?page="+page);
		return new ResponseEntity<ResponseArticlesDto>(HttpStatus.OK);
	}
	
	// Article 보기 
	@GetMapping("/article/{id}")
	public ResponseEntity<Article> article(@PathVariable("id") int id) {
		articleService.getArticle(id);
		return new ResponseEntity<>( HttpStatus.OK);
	}
	
	// 글쓰기페이지 요청
	@GetMapping("/article/create")
	public String articleCreateForm() {
		return "articlePages/articleCreate";
	}
	
	// Article 생성
	@PostMapping("/article")
	public ResponseEntity<HttpStatus> create(@RequestBody ArticleCreateDto dto){
		logger.info("dto.getDate : "+dto.getTitle());
		logger.info("POST /article");
		return new ResponseEntity<HttpStatus>(HttpStatus.OK);
	}
	
	// Article 수정
	@PatchMapping("/article/{id}")
	public ResponseEntity<HttpStatus> create(@PathVariable("id") int id) {
		logger.info("PATCH /article/" + id);
		return new ResponseEntity<HttpStatus>(HttpStatus.OK);
	}

	// Article 삭제
	@DeleteMapping("/article/{id}")
	public ResponseEntity<HttpStatus> delete(@PathVariable("id") int id) {
		logger.info("DELETE /article/" + id);
		return new ResponseEntity<HttpStatus>(HttpStatus.OK);
	}

}
