package jsh.project.board.article.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import jsh.project.board.article.dao.ArticleDao;
import jsh.project.board.article.dto.Article;
import jsh.project.board.article.dto.ArticleResponseDto;
import jsh.project.board.article.dto.RequestArticlesDto;
import jsh.project.board.article.dto.ResponseArticlesDto;
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
		Pagination pagination = new Pagination(articleDao.getTotalCount(dto), dto.getPage(),articleDao.getNoticeTotalCount());
		dto.setStartCount(pagination.getStartCount());
		dto.setEndCount(pagination.getEndCount());
		
		List<ArticleResponseDto> articles = new ArrayList<>();
		articles.addAll(articleDao.selectNoticeArticles(pagination.getNoticeScope()));
		articles.addAll(articleDao.selectArticles(dto));
		
		ResponseArticlesDto responseArticles = dto.toResponseDto();
		responseArticles.setArticles(articles);
		responseArticles.setPagination(pagination);
		return responseArticles;
	}
	
	public ResponseArticlesDto getAccountArticles(RequestArticlesDto dto){
		log.info(dto.toString());
		Pagination pagination = new Pagination(articleDao.getTotalCount(dto), dto.getPage(), 0);
		dto.setStartCount(pagination.getStartCount());
		dto.setEndCount(pagination.getEndCount());
		
		ResponseArticlesDto responseArticles = dto.toResponseDto();
		responseArticles.setArticles(articleDao.selectArticles(dto));
		responseArticles.setPagination(pagination);
		return responseArticles;
	} 
	
	public Article getArticle(int id) {
		return articleDao.selectArticle(id);
	}
	
}
