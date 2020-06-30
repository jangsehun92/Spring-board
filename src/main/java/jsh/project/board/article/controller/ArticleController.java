package jsh.project.board.article.controller;

import java.security.Principal;

import javax.servlet.http.HttpServletRequest;
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
import jsh.project.board.account.enums.Role;
import jsh.project.board.article.dto.request.RequestArticleCreateDto;
import jsh.project.board.article.dto.request.RequestArticleDeleteDto;
import jsh.project.board.article.dto.request.RequestArticleDetailDto;
import jsh.project.board.article.dto.request.RequestArticleInfoDto;
import jsh.project.board.article.dto.request.RequestArticleUpdateDto;
import jsh.project.board.article.dto.request.RequestArticlesDto;
import jsh.project.board.article.dto.request.like.RequestLikeDto;
import jsh.project.board.article.dto.response.ResponseArticleUpdateDto;
import jsh.project.board.article.dto.response.ResponseBoardDto;
import jsh.project.board.article.enums.mapper.CategoryEnumMapper;
import jsh.project.board.article.enums.mapper.ImportanceEnumMapper;
import jsh.project.board.article.service.ArticleService;

@Controller
public class ArticleController {
	
	private static final Logger log = LoggerFactory.getLogger(ArticleController.class);
	
	private ArticleService articleService;
	private CategoryEnumMapper categoryEnumMapper;
	private ImportanceEnumMapper importanceEnumMapper;
	
	public ArticleController(ArticleService articleService, CategoryEnumMapper categoryEnumMapper, ImportanceEnumMapper importanceEnumMapper) {
		this.articleService = articleService;
		this.categoryEnumMapper = categoryEnumMapper;
		this.importanceEnumMapper = importanceEnumMapper;
	}
	
	// 공지사항 Aritcles
	@GetMapping("/")
	public String root(){
		return "redirect:/articles/notice";
	}
	
	// category별 Aritcles 
	@GetMapping("/articles/{category}")
	public String articleListByCategory(@PathVariable String category, RequestArticlesDto dto, Model model){
		log.info("GET /articles/"+category+"?page="+dto.getPage());
		dto.setCategory(categoryEnumMapper.getCategory(category));
		ResponseBoardDto responseBoardDto = categoryEnumMapper.isNoticeCategory(dto.getCategory())?articleService.getNoticeArticles(dto):articleService.getArticles(dto);
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
		log.info("GET /article/"+id);
		if(principal != null) {
			Account account = (Account)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			dto.setAccountId(account.getId());
		}
		dto.setId(id);
		model.addAttribute("responseDto",articleService.getArticle(dto));
		return "articlePages/articleDetail";
	}
	
	// 해당 게시글 추천
	@PreAuthorize("(#dto.accountId == principal.id) and (#dto.articleId == #id)")
	@PostMapping("/article/like/{id}")
	public @ResponseBody ResponseEntity<HttpStatus> like(@PathVariable("id")int id, @RequestBody RequestLikeDto dto){
		log.info("POST /article/like/"+id);
		articleService.like(dto);
		return new ResponseEntity<HttpStatus>(HttpStatus.OK);
	}
	
	// 글 작성 페이지 요청(일반)
	@GetMapping("/articles/{category}/create")
	public String articleCreateForm(@PathVariable("category") String category, Model model) {
		log.info("POST /articles/"+category+"/create");
		model.addAttribute("category", categoryEnumMapper.getCategory(category));
		model.addAttribute("categorys", categoryEnumMapper.getUserCategory());
		return "articlePages/articleCreatePage";
	}
	
	// 글 작성 페이지 요청(관리자)
	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	@GetMapping("/admin/articles/{category}/create")
	public String adminArticleCreateForm(@PathVariable("category") String category, Model model) {
		log.info("POST /admin/articles/"+category+"/create");
		model.addAttribute("category", categoryEnumMapper.getCategory(category));
		model.addAttribute("categorys", categoryEnumMapper.getAdminCategory());
		model.addAttribute("articleImportance", importanceEnumMapper.getImportanceList());
		return "articlePages/articleCreatePage";
	}
	
	// 글 수정 페이지 요청
	@PreAuthorize("((#dto.accountId == principal.id) and (#dto.id == #id)) or (hasAuthority('ROLE_ADMIN'))")
	@PostMapping("/article/edit/{id}")
	public String articleUpdateForm(@PathVariable("id") int id, Model model, RequestArticleInfoDto dto, HttpServletRequest request) {
		log.info("POST /article/edit/"+id);
		ResponseArticleUpdateDto responseDto = articleService.getUpdateArticle(dto.getId());
		model.addAttribute("category", categoryEnumMapper.getCategory(responseDto.getCategory()));
		model.addAttribute("categorys", categoryEnumMapper.getCategorys(responseDto.getCategory()));
		if(request.isUserInRole(Role.ADMIN.getValue()) && categoryEnumMapper.isNoticeCategory(responseDto.getCategory())) {
			model.addAttribute("articleImportance", importanceEnumMapper.getImportanceList());
		}
		model.addAttribute("responseDto", responseDto);
		return "articlePages/articleUpdatePage";
	}
	
	// 글 작성
	@PreAuthorize("(#dto.accountId == principal.id)")
	@PostMapping("/article")
	public ResponseEntity<Integer> createArticle(@RequestBody @Valid RequestArticleCreateDto dto){
		log.info("POST /article");
		log.info(dto.toString());
		return new ResponseEntity<>(articleService.createArticle(dto),HttpStatus.OK);
	}
	
	// 글 수정
	@PreAuthorize("((#dto.accountId == principal.id) and (#dto.id == #id)) or (hasAuthority('ROLE_ADMIN'))")
	@PatchMapping("/article/{id}")
	public ResponseEntity<HttpStatus> updateArticle(@PathVariable("id") int id, @RequestBody @Valid RequestArticleUpdateDto dto) {
		log.info("PATCH /article/" + id);
		articleService.updateArticle(dto);
		return new ResponseEntity<HttpStatus>(HttpStatus.OK);
	}

	// 글 삭제
	@PreAuthorize("((#dto.accountId == principal.id) and (#dto.articleId == #id)) or (hasAuthority('ROLE_ADMIN'))")
	@DeleteMapping("/article/{id}")
	public ResponseEntity<HttpStatus> deleteArticle(@PathVariable("id") int id, @RequestBody RequestArticleDeleteDto dto) {
		log.info("DELETE /article/" + id);
		articleService.deleteArticle(id);
		return new ResponseEntity<HttpStatus>(HttpStatus.OK);
	}
	
	// 파일 업로드
	@PostMapping("/article/image")
	public ResponseEntity<String> imageUpload(@RequestParam("file") MultipartFile file){
		log.info("POST /article/image uploadFileInfo { name : "+file.getOriginalFilename() + " size :  "+ file.getSize() + " type : " + file.getContentType() + " } ");
		return new ResponseEntity<>(articleService.uploadFile(file),HttpStatus.OK);
	}
}
