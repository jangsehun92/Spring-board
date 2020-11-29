package jsh.project.board.article.controller;

import java.security.Principal;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import jsh.project.board.account.domain.Account;
import jsh.project.board.account.enums.Role;
import jsh.project.board.article.dto.request.article.RequestArticleCreateDto;
import jsh.project.board.article.dto.request.article.RequestArticleDeleteDto;
import jsh.project.board.article.dto.request.article.RequestArticleDetailDto;
import jsh.project.board.article.dto.request.article.RequestArticleInfoDto;
import jsh.project.board.article.dto.request.article.RequestArticleUpdateDto;
import jsh.project.board.article.dto.request.article.RequestArticlesDto;
import jsh.project.board.article.dto.request.like.RequestLikeDto;
import jsh.project.board.article.dto.response.ResponseArticleUpdateDto;
import jsh.project.board.article.dto.response.ResponseBoardDto;
import jsh.project.board.article.enums.mapper.CategoryEnumMapper;
import jsh.project.board.article.enums.mapper.ImportanceEnumMapper;
import jsh.project.board.article.service.ArticleService;

@Controller
public class ArticleController {
	
	private final ArticleService articleService;
	private final CategoryEnumMapper categoryEnumMapper;
	private final ImportanceEnumMapper importanceEnumMapper;
	
	public ArticleController(ArticleService articleService, CategoryEnumMapper categoryEnumMapper, ImportanceEnumMapper importanceEnumMapper) {
		this.articleService = articleService;
		this.categoryEnumMapper = categoryEnumMapper;
		this.importanceEnumMapper = importanceEnumMapper;
	}
	
	// 공지사항 Aritcles
	@RequestMapping("/")
	public String root(){
		return "redirect:/articles/notice";
	}
	
	// category별 Aritcles 
	@GetMapping("/articles/{category}")
	public String articleListByCategory(@PathVariable String category, RequestArticlesDto dto, Model model){
		dto.setCategory(categoryEnumMapper.getCategory(category));
		final ResponseBoardDto responseBoardDto = categoryEnumMapper.isNoticeCategory(dto.getCategory())?
				articleService.getNoticeArticles(dto) : articleService.getArticles(dto);
		model.addAttribute("responseBoardDto", responseBoardDto);
		return "articlePages/articles";
	}
		
	// 해당 유저의 Aritcles
	@GetMapping("/articles/account/{id}")
	public @ResponseBody ResponseEntity<ResponseBoardDto> articleListByAccount(@PathVariable int id, RequestArticlesDto dto){
		dto.setAccountId(id);
		
		final ResponseBoardDto responseArticlesDto = articleService.getAccountArticles(dto);
		return new ResponseEntity<>(responseArticlesDto, HttpStatus.OK);
	}
	
	// 단일 Article 보기 
	@GetMapping("/article/{id}") 
	public String article(@PathVariable("id") int id, Model model, Principal principal, RequestArticleDetailDto dto) {
		if(principal != null) {
			final Account account = (Account)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			dto.setAccountId(account.getId());
		}
		model.addAttribute("responseDto",articleService.getArticle(dto));
		return "articlePages/articleDetail";
	}
	
	// 해당 게시글 추천
	@PreAuthorize("(#dto.accountId == principal.id) and (#dto.articleId == #id)")
	@PostMapping("/article/like/{id}")
	public @ResponseBody ResponseEntity<HttpStatus> like(@PathVariable("id")int id, @RequestBody RequestLikeDto dto){
		articleService.like(dto);
		return new ResponseEntity<HttpStatus>(HttpStatus.OK);
	}
	
	// 글 작성 페이지 요청(일반)
	@PreAuthorize("hasAnyRole('ROLE_USER','ROLE_ADMIN')")
	@GetMapping("/articles/{category}/create")
	public String articleCreateForm(@PathVariable("category") String category, Model model) {
		model.addAttribute("category", categoryEnumMapper.getCategory(category));
		model.addAttribute("categorys", categoryEnumMapper.getUserCategory());
		return "articlePages/articleCreatePage";
	}
	
	// 글 작성 페이지 요청(관리자)
	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	@GetMapping("/admin/articles/{category}/create")
	public String adminArticleCreateForm(@PathVariable("category") String category, Model model) {
		model.addAttribute("category", categoryEnumMapper.getCategory(category));
		model.addAttribute("categorys", categoryEnumMapper.getAdminCategory());
		model.addAttribute("articleImportance", importanceEnumMapper.getImportanceList());
		return "articlePages/articleCreatePage";
	}
	
	// 글 수정 페이지 요청
	@PreAuthorize("((#dto.accountId == principal.id) and (#dto.id == #id)) or (hasAuthority('ROLE_ADMIN'))")
	@PostMapping("/article/edit/{id}")
	public String articleUpdateForm(@PathVariable("id") int id, Model model, RequestArticleInfoDto dto, HttpServletRequest request) {
		final ResponseArticleUpdateDto responseDto = articleService.getUpdateArticle(dto.getId());
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
		return new ResponseEntity<>(articleService.createArticle(dto),HttpStatus.OK);
	}
	
	// 글 수정
	@PreAuthorize("((#dto.accountId == principal.id) and (#dto.id == #id)) or (hasAuthority('ROLE_ADMIN'))")
	@PatchMapping("/article/{id}")
	public ResponseEntity<HttpStatus> updateArticle(@PathVariable("id") int id, @RequestBody @Valid RequestArticleUpdateDto dto) {
		articleService.updateArticle(dto);
		return new ResponseEntity<HttpStatus>(HttpStatus.OK);
	}

	// 글 삭제
	@PreAuthorize("((#dto.accountId == principal.id) and (#dto.articleId == #id)) or (hasAuthority('ROLE_ADMIN'))")
	@DeleteMapping("/article/{id}")
	public ResponseEntity<HttpStatus> deleteArticle(@PathVariable("id") int id, @RequestBody RequestArticleDeleteDto dto) {
		articleService.deleteArticle(dto);
		return new ResponseEntity<HttpStatus>(HttpStatus.OK);
	}
	
	// 이미지 파일 업로드
	@PostMapping("/article/image")
	public ResponseEntity<String> imageUpload(@RequestParam("file") MultipartFile file){
		return new ResponseEntity<>(articleService.uploadFile(file),HttpStatus.OK);
	}
}
