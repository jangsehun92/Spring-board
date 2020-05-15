package jsh.project.board.article.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
		ResponseArticlesDto responseArticles = new ResponseArticlesDto();
		Pagination pagination = new Pagination(articleDao.getTotalCount(dto), dto.getPage());
		for(int i = 0; i < 10; i++) {
			System.out.println((i - 1) * 10 + 1);
		}
		System.out.println("페이지네이션 만들고나서의 page 값 : " + dto.getPage() + dto.getStartCount() + dto.getEndCount());
//		Map<String, Object> paramMap = new HashMap<>();
//		paramMap.put("startCount", pagination.getStartCount());
//		paramMap.put("endCount", pagination.getEndCount());
		dto.setStartCount(pagination.getStartCount());
		dto.setEndCount(pagination.getEndCount());
		
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
