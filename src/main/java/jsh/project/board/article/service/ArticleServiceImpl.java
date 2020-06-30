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
import jsh.project.board.article.dto.request.RequestArticleCreateDto;
import jsh.project.board.article.dto.request.RequestArticleDetailDto;
import jsh.project.board.article.dto.request.RequestArticleUpdateDto;
import jsh.project.board.article.dto.request.RequestArticlesDto;
import jsh.project.board.article.dto.request.like.RequestLikeDto;
import jsh.project.board.article.dto.response.ResponseArticleDetailDto;
import jsh.project.board.article.dto.response.ResponseArticleDto;
import jsh.project.board.article.dto.response.ResponseArticleUpdateDto;
import jsh.project.board.article.dto.response.ResponseBoardDto;
import jsh.project.board.article.exception.ArticleNotFoundException;
import jsh.project.board.global.infra.util.Pagination;
import jsh.project.board.global.infra.util.FileService;

@Service
public class ArticleServiceImpl implements ArticleService{
	private static final Logger log = LoggerFactory.getLogger(ArticleServiceImpl.class);
	
	private ArticleDao articleDao;
	private FileService fileService;
	
	public ArticleServiceImpl(ArticleDao articleDao, FileService fileService) {
		this.articleDao = articleDao;
		this.fileService = fileService;
	}
	
	@Override
	public ResponseBoardDto getNoticeArticles(RequestArticlesDto dto){
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
	public ResponseBoardDto getArticles(RequestArticlesDto dto){
		log.info(dto.toString());
		Pagination pagination = new Pagination(articleDao.selectTotalCount(dto), dto.getPage(),articleDao.selectNoticeTotalCount());
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
	public ResponseBoardDto getAccountArticles(RequestArticlesDto dto){
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
	public ResponseArticleDetailDto getArticle(RequestArticleDetailDto dto) {
		log.info(dto.toString());
		ResponseArticleDetailDto responseDto = articleDao.selectArticle(dto.getId());
		if(responseDto == null) {
			throw new ArticleNotFoundException();
		}
		articleDao.updateViewCount(dto.getId());
		responseDto.setLikeCheck(articleDao.articleLikeCheck(dto.toLikeDto()));
		log.info(responseDto.toString());
		return responseDto;
	}
	
	@Transactional
	@Override
	public int createArticle(RequestArticleCreateDto dto) {
		Article article = dto.toArticle();
		articleDao.insertArticle(article);
		log.info(article.toString());
		return article.getId();
	}
	
	@Transactional
	@Override
	public ResponseArticleUpdateDto getUpdateArticle(int id) {
		return articleDao.selectUpdateArticle(id);
	}
	
	@Transactional
	@Override
	public void updateArticle(RequestArticleUpdateDto dto) {
		articleDao.updateArticle(dto.toArticle());
	}
	
	@Transactional
	@Override
	public void deleteArticle(int id) {
		articleDao.deleteArticle(id);
		articleDao.deleteReplys(id);
		articleDao.deleteLikes(id);
	}
	
	@Transactional
	@Override
	public void like(RequestLikeDto dto) {
		if(articleDao.articleLikeCheck(dto)==0) {
			articleDao.insertLike(dto);
		}else {
			articleDao.deleteLike(dto);
		}
	}
	
	@Override
	public String uploadFile(MultipartFile file) {
		return fileService.upload(file);
	}
	
}
