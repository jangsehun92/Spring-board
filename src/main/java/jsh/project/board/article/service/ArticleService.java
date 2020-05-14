package jsh.project.board.article.service;

import java.util.List;

import org.springframework.stereotype.Service;

import jsh.project.board.article.dao.ArticleDao;
import jsh.project.board.article.dto.Article;

@Service
public class ArticleService {
	
	private ArticleDao articleDao;
	
	public ArticleService(ArticleDao articleDao) {
		this.articleDao = articleDao;
	}
	
	public List<Article> getArticles(int page){
		return articleDao.selectArticles();
	}
	
	public List<Article> getAccountArticles(int id){
		return articleDao.selectAccountArticles(id);
	} 
	
	public Article getArticle(int id) {
		return articleDao.selectArticle(id);
	}
	
}
