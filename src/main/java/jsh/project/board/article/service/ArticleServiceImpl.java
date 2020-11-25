package jsh.project.board.article.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import jsh.project.board.article.dao.ArticleDao;
import jsh.project.board.article.domain.Article;
import jsh.project.board.article.dto.request.article.RequestArticleCreateDto;
import jsh.project.board.article.dto.request.article.RequestArticleDeleteDto;
import jsh.project.board.article.dto.request.article.RequestArticleDetailDto;
import jsh.project.board.article.dto.request.article.RequestArticleUpdateDto;
import jsh.project.board.article.dto.request.article.RequestArticlesDto;
import jsh.project.board.article.dto.request.like.RequestLikeDto;
import jsh.project.board.article.dto.response.ResponseArticleDetailDto;
import jsh.project.board.article.dto.response.ResponseArticleDto;
import jsh.project.board.article.dto.response.ResponseArticleUpdateDto;
import jsh.project.board.article.dto.response.ResponseBoardDto;
import jsh.project.board.article.exception.ArticleNotFoundException;
import jsh.project.board.article.exception.ArticlesNotFoundException;
import jsh.project.board.global.infra.util.Pagination;
import jsh.project.board.global.infra.util.FileService;

@Service
public class ArticleServiceImpl implements ArticleService{
	private static final Logger log = LoggerFactory.getLogger(ArticleServiceImpl.class);
	
	private final ArticleDao articleDao;
	private final FileService fileService;
	
	public ArticleServiceImpl(ArticleDao articleDao, FileService fileService) {
		this.articleDao = articleDao;
		this.fileService = fileService;
	}
	
	@Override
	public ResponseBoardDto getNoticeArticles(final RequestArticlesDto dto){
		log.info(dto.toString());
		Pagination pagination = new Pagination(articleDao.selectTotalCount(dto), dto.getPage());
		dto.setStartCount(pagination.getStartCount());
		dto.setEndCount(pagination.getEndCount());
		
		ResponseBoardDto responseArticles = dto.toResponseDto();
		responseArticles.setArticles(articleDao.selectArticles(dto));
		responseArticles.setPagination(pagination);
		return responseArticles;
	}
	
	@Override
	public ResponseBoardDto getArticles(final RequestArticlesDto dto){
		log.info(dto.toString());
		Pagination pagination = new Pagination(	articleDao.selectTotalCount(dto), 
												dto.getPage(),
												articleDao.selectNoticeTotalCount());
		dto.setStartCount(pagination.getStartCount());
		dto.setEndCount(pagination.getEndCount());
		
		List<ResponseArticleDto> articles = new ArrayList<>();
		articles.addAll(articleDao.selectNoticeArticles(pagination.getNoticeScope()));
		articles.addAll(articleDao.selectArticles(dto));
		
		ResponseBoardDto responseArticles = dto.toResponseDto();
		responseArticles.setArticles(articles);
		responseArticles.setPagination(pagination);
		return responseArticles;
	}
	
	@Override
	public ResponseBoardDto getAccountArticles(final RequestArticlesDto dto){
		log.info(dto.toString());
		Pagination pagination = new Pagination(articleDao.selectTotalCount(dto), dto.getPage());
		dto.setStartCount(pagination.getStartCount());
		dto.setEndCount(pagination.getEndCount());
		
		List<ResponseArticleDto> articles = articleDao.selectArticles(dto);
		if(articles.isEmpty()) throw new ArticlesNotFoundException();
		
		ResponseBoardDto responseArticles = dto.toResponseDto();
		responseArticles.setArticles(articles);
		responseArticles.setPagination(pagination);
		return responseArticles;
	}
	
	@Override
	public ResponseArticleDetailDto getArticle(final RequestArticleDetailDto dto) {
		log.info(dto.toString());
		ResponseArticleDetailDto responseDto = articleDao.selectArticle(dto.getId());
		if(responseDto == null) throw new ArticleNotFoundException();
		
		articleDao.updateViewCount(dto.getId());
		responseDto.setLikeCheck(articleDao.selectArticleLikeCheck(dto.toLikeDto()));
		log.info(responseDto.toString());
		return responseDto;
	}
	
	@Transactional
	@Override
	public int createArticle(final RequestArticleCreateDto dto) {
		log.info(dto.toString());
		Article article = dto.toArticle();
		articleDao.insertArticle(article);
		log.info(article.toString());
		return article.getId();
	}
	
	@Transactional
	@Override
	public ResponseArticleUpdateDto getUpdateArticle(final int id) {
		ResponseArticleUpdateDto dto = articleDao.selectUpdateArticle(id);
		log.info(dto.toString());
		return dto;
	}
	
	@Transactional
	@Override
	public void updateArticle(final RequestArticleUpdateDto dto) {
		log.info(dto.toString());
		Article article = dto.toArticle();
		if(article == null) throw new ArticleNotFoundException();
		log.info(article.toString());
		articleDao.updateArticle(article);
	}
	
	@Transactional
	@Override
	public void deleteArticle(final RequestArticleDeleteDto dto) {
		log.info("deleteArticle : " + dto.getArticleId());
		if(articleDao.selectArticleCheck(dto.getArticleId()) == 0) throw new ArticleNotFoundException();
		articleDao.deleteArticle(dto.getArticleId());
		articleDao.deleteReplys(dto.getArticleId());
		articleDao.deleteLikes(dto.getArticleId());
	}
	
	@Transactional
	@Override
	public void like(final RequestLikeDto dto) {
		log.info(dto.toString());
		if(articleDao.selectArticleCheck(dto.getArticleId()) == 0) throw new ArticleNotFoundException();
		if(articleDao.selectArticleLikeCheck(dto) == 0) {
			articleDao.insertLike(dto);
			log.info("insertLike");
		}else {
			articleDao.deleteLike(dto);
			log.info("deleteLike");
		}
	}
	
	@Override
	public String uploadFile(final MultipartFile file) {
		log.info("uploadFileInfo { name : "+file.getOriginalFilename() + " size :  "+ file.getSize() + " type : " + file.getContentType() + " } ");
		return fileService.upload(file);
	}
	
}
