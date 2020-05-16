package jsh.project.board.article.service;

import java.util.List;

import org.springframework.stereotype.Service;

import jsh.project.board.article.dao.ArticleDao;
import jsh.project.board.article.dto.Article;
import jsh.project.board.article.dto.RequestArticlesDto;
import jsh.project.board.article.dto.ResponseArticlesDto;
import jsh.project.board.global.infra.util.Pagination;

@Service
public class ArticleService {
	
	private ArticleDao articleDao;
	
	public ArticleService(ArticleDao articleDao) {
		this.articleDao = articleDao;
	}
	
	public ResponseArticlesDto getArticles(RequestArticlesDto dto){
		Pagination pagination = new Pagination(articleDao.getTotalCount(dto), dto.getPage());
		dto.setStartCount(pagination.getStartCount());
		dto.setEndCount(pagination.getEndCount());
		
		ResponseArticlesDto responseArticles = dto.toResponseDto();
		responseArticles.setArticles(articleDao.selectArticles(dto));
		responseArticles.setPagination(pagination);
		return responseArticles;
	}
	
	public List<Article> getAccountArticles(int id){
		return articleDao.selectAccountArticles(id);
	} 
	
	public Article getArticle(int id) {
		return articleDao.selectArticle(id);
	}
	
}
