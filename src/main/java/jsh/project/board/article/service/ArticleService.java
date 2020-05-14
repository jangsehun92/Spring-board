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
	
	public List<Article> getArticleList(){
		return articleDao.selectArticleList();
	}
	
	public List<Article> getAccountArticleList(int id){
		return articleDao.selectAccountArticleList(id);
	} 
	
	public Article getArticle(int id) {
		return articleDao.selectArticle(id);
	}
	
}
