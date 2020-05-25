package jsh.project.board.article.controller;

import java.security.Principal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import jsh.project.board.account.dto.Account;
import jsh.project.board.article.dto.RequestArticleCreateDto;
import jsh.project.board.article.dto.RequestArticleDetailDto;
import jsh.project.board.article.dto.RequestArticlesDto;
import jsh.project.board.article.dto.ResponseArticlesDto;
import jsh.project.board.article.dto.like.RequestLikeDto;
import jsh.project.board.article.exception.NonLoginException;
import jsh.project.board.article.service.ArticleService;

@Controller
public class ArticleController {
	
	private static final Logger log = LoggerFactory.getLogger(ArticleController.class);
	
	private ArticleService articleService;
	
	public ArticleController(ArticleService articleService) {
		this.articleService = articleService;
	}
	
	// 공지사항 Aritcles
	@GetMapping("/")
	public String articleList(RequestArticlesDto dto, Model model){
		log.info("GET /articles/"+dto.getPage());
		dto.setCategory("notice");
		ResponseArticlesDto responseArticlesDto = articleService.getArticles(dto);
		model.addAttribute("responseArticlesDto", responseArticlesDto);
		return "articlePages/articles";
	}
	
	// category별 Aritcles 
	@GetMapping("/articles/{category}")
	public String articleListByCategory(@PathVariable String category, RequestArticlesDto dto, Model model){
		log.info("GET /articles/"+category+"?page="+dto.getPage());
		dto.setCategory(category);
		ResponseArticlesDto responseArticlesDto = articleService.getArticles(dto);
		model.addAttribute("responseArticlesDto", responseArticlesDto);
		return "articlePages/articles";
	}
		
	// 해당 유저의 Aritcles
	@GetMapping("/articles/account/{id}")
	public @ResponseBody ResponseEntity<ResponseArticlesDto> articleListByAccount(@PathVariable int id, RequestArticlesDto dto){
		log.info("GET /articles/account/"+id+"?page="+dto.getPage());
		dto.setAccountId(id);
		ResponseArticlesDto responseArticlesDto = articleService.getAccountArticles(dto);
		return new ResponseEntity<>(responseArticlesDto, HttpStatus.OK);
	}
	
	// 단일 Article 보기 
	@GetMapping("/article/{id}")
	public String article(@PathVariable("id") int id, Model model, Principal principal, RequestArticleDetailDto dto) {
		if(principal != null) {
			Account account = (Account)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			dto.setAccountId(account.getId());
		}
		dto.setId(id);
		model.addAttribute("responseDto",articleService.getArticle(dto));
		return "articlePages/articleDetail";
	}
	
	@PostMapping("/article/like/{id}")
	public @ResponseBody ResponseEntity<HttpStatus> like(@PathVariable("id")int id, Principal principal, RequestLikeDto dto){
		if(principal == null) {
			throw new NonLoginException();
		}
		Account account = (Account)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		dto.setArticleId(id);
		dto.setAccountId(account.getId());
		articleService.like(dto);
		return new ResponseEntity<HttpStatus>(HttpStatus.OK);
	}
	
	// 글쓰기 페이지 요청
	@GetMapping("/article/create")
	public String articleCreateForm() {
		return "articlePages/articleCreate";
	}
	
	// 글수정 페이지 요청
	@GetMapping("/article/edit/{id}")
	public String articleUpdateForm(@PathVariable("id") int id, Model model) {
		return "articlePages/articleUpdate";
	}
	
	// Article 생성
	@PostMapping("/article")
	public ResponseEntity<HttpStatus> create(@RequestBody RequestArticleCreateDto dto){
		log.info("dto.getDate : "+dto.getTitle());
		log.info("POST /article");
		return new ResponseEntity<HttpStatus>(HttpStatus.OK);
	}
	
	// Article 수정
	@PatchMapping("/article/{id}")
	public ResponseEntity<HttpStatus> create(@PathVariable("id") int id) {
		log.info("PATCH /article/" + id);
		return new ResponseEntity<HttpStatus>(HttpStatus.OK);
	}

	// Article 삭제
	@DeleteMapping("/article/{id}")
	public ResponseEntity<HttpStatus> delete(@PathVariable("id") int id) {
		log.info("DELETE /article/" + id);
		return new ResponseEntity<HttpStatus>(HttpStatus.OK);
	}

}
