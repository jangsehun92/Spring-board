package jsh.project.board.article.controller;

import java.security.Principal;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import jsh.project.board.account.domain.Account;
import jsh.project.board.article.dto.request.RequestArticleCreateDto;
import jsh.project.board.article.dto.request.RequestArticleDeleteDto;
import jsh.project.board.article.dto.request.RequestArticleDetailDto;
import jsh.project.board.article.dto.request.RequestArticleUpdateDto;
import jsh.project.board.article.dto.request.RequestArticlesDto;
import jsh.project.board.article.dto.request.like.RequestLikeDto;
import jsh.project.board.article.dto.response.ResponseArticleUpdateDto;
import jsh.project.board.article.dto.response.ResponseBoardDto;
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
	public String articleList(){
		return "redirect:/articles/notice";
	}
	
	// category별 Aritcles 
	@GetMapping("/articles/{category}")
	public String articleListByCategory(@PathVariable String category, RequestArticlesDto dto, Model model){
		log.info("GET /articles/"+category+"?page="+dto.getPage());
		dto.setCategory(category);
		ResponseBoardDto responseBoardDto = articleService.getArticles(dto);
		model.addAttribute("responseBoardDto", responseBoardDto);
		return "articlePages/articles";
	}
		
	// 해당 유저의 Aritcles
	@GetMapping("/articles/account/{id}")
	public @ResponseBody ResponseEntity<ResponseBoardDto> articleListByAccount(@PathVariable int id, RequestArticlesDto dto){
		log.info("GET /articles/account/"+id+"?page="+dto.getPage());
		dto.setAccountId(id);
		ResponseBoardDto responseArticlesDto = articleService.getAccountArticles(dto);
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
	
	// 추천
	@PreAuthorize("(#dto.accountId == principal.id) and (#dto.articleId == #id)")
	@PostMapping("/article/like/{id}")
	public @ResponseBody ResponseEntity<HttpStatus> like(@PathVariable("id")int id, @RequestBody RequestLikeDto dto){
		articleService.like(dto);
		return new ResponseEntity<HttpStatus>(HttpStatus.OK);
	}
	
	// 글 작성 페이지 요청
	@GetMapping("/articles/{category}/create")
	public String articleCreateForm(@PathVariable("category") String category, Model model) {
		model.addAttribute("category", category);
		return "articlePages/articleCreatePage";
	}
	
	// 글 수정 페이지 요청
	@GetMapping("/article/edit/{id}")
	public String articleUpdateForm(@PathVariable("id") int id, Model model) {
		Account account = (Account)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		ResponseArticleUpdateDto dto = articleService.getUpdateArticle(id);
		if(account.getId() != dto.getAccountId()) {
			return "redirect:/";
		}
		model.addAttribute("responseArticleUpdateDto", dto);
		return "articlePages/articleUpdatePage";
		
	}
	
	// 글 작성
	@PreAuthorize("(#dto.accountId == principal.id)")
	@PostMapping("/article")
	public ResponseEntity<String> create(@RequestBody @Valid RequestArticleCreateDto dto){
		log.info("POST /article");
		return new ResponseEntity<>(Integer.toString(articleService.createArticle(dto)),HttpStatus.OK);
	}
	
	// 글 수정
	@PreAuthorize("(#dto.accountId == principal.id) and (#dto.id == #id)")
	@PatchMapping("/article/{id}")
	public ResponseEntity<HttpStatus> update(@PathVariable("id") int id, @RequestBody RequestArticleUpdateDto dto) {
		log.info("PATCH /article/" + id);
		articleService.updateArticle(dto);
		return new ResponseEntity<HttpStatus>(HttpStatus.OK);
	}

	// 글 삭제
	@PreAuthorize("(#dto.accountId == principal.id) and (#dto.articleId == #id)")
	@DeleteMapping("/article/{id}")
	public ResponseEntity<HttpStatus> delete(@PathVariable("id") int id, @RequestBody RequestArticleDeleteDto dto) {
		articleService.deleteArticle(dto);
		log.info("DELETE /article/" + id);
		return new ResponseEntity<HttpStatus>(HttpStatus.OK);
	}
	
	// 파일 업로드
	@PostMapping("/article/image")
	public ResponseEntity<String> imageUpload(@RequestParam("file") MultipartFile file){
		return new ResponseEntity<>(articleService.uploadFile(file),HttpStatus.OK);
	}
}
