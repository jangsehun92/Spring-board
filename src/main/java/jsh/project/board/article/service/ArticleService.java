package jsh.project.board.article.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jsh.project.board.article.dao.ArticleDao;
import jsh.project.board.article.dto.ArticleResponseDto;
import jsh.project.board.article.dto.RequestArticleDeleteDto;
import jsh.project.board.article.dto.RequestArticleDetailDto;
import jsh.project.board.article.dto.RequestArticlesDto;
import jsh.project.board.article.dto.ResponseArticleDetailDto;
import jsh.project.board.article.dto.ResponseArticlesDto;
import jsh.project.board.article.dto.like.RequestLikeDto;
import jsh.project.board.global.infra.util.Pagination;

@Service
public class ArticleService {
	private static final Logger log = LoggerFactory.getLogger(ArticleService.class);
	
	private ArticleDao articleDao;
	
	public ArticleService(ArticleDao articleDao) {
		this.articleDao = articleDao;
	}
	
	public ResponseArticlesDto getArticles(RequestArticlesDto dto){
		log.info(dto.toString());
		Pagination pagination = new Pagination(articleDao.selectTotalCount(dto), dto.getPage(),articleDao.selectNoticeTotalCount());
		dto.setStartCount(pagination.getStartCount());
		dto.setEndCount(pagination.getEndCount());
		
		List<ArticleResponseDto> articles = new ArrayList<>();
		articles.addAll(articleDao.selectNoticeArticles(pagination.getNoticeScope()));
		articles.addAll(articleDao.selectArticles(dto));
		
		ResponseArticlesDto responseArticles = dto.getResponseDto();
		responseArticles.setArticles(articles);
		responseArticles.setPagination(pagination);
		return responseArticles;
	}
	
	public ResponseArticlesDto getAccountArticles(RequestArticlesDto dto){
		log.info(dto.toString());
		Pagination pagination = new Pagination(articleDao.selectTotalCount(dto), dto.getPage());
		dto.setStartCount(pagination.getStartCount());
		dto.setEndCount(pagination.getEndCount());
		
		ResponseArticlesDto responseArticles = dto.getResponseDto();
		responseArticles.setArticles(articleDao.selectArticles(dto));
		responseArticles.setPagination(pagination);
		return responseArticles;
	}
	
	public ResponseArticleDetailDto getArticle(RequestArticleDetailDto dto) {
		log.info(dto.toString());
		articleDao.updateViewCount(dto.getId());
		ResponseArticleDetailDto responseDto = articleDao.selectArticle(dto.getId());
		responseDto.setLikeCheck(articleDao.articleLikeCheck(dto.getLikeDto()));
		return responseDto;
	}
	
	@Transactional
	public void deleteArticle(RequestArticleDeleteDto dto) {
		articleDao.deleteArticle(dto);
		articleDao.deleteReplys(dto.getArticleId());
		articleDao.deleteLikes(dto.getAccountId());
	}
	
	public void like(RequestLikeDto dto) {
		if(articleDao.articleLikeCheck(dto)==0) {
			articleDao.insertLike(dto);
		}else {
			articleDao.deleteLike(dto);
		}
	}
	
}
