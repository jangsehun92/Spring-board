package jsh.project.board.article.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import jsh.project.board.article.dto.Article;
import jsh.project.board.article.dto.ArticleResponseDto;

@Repository
public class ArticleDao {
	
	private SqlSession sqlSession;
	
	public ArticleDao(SqlSession sqlSession) {
		this.sqlSession = sqlSession;
	}
	
	public int getTotalCount() {
		return sqlSession.selectOne("boardMapper.totalCount");
	}
	
	public List<ArticleResponseDto> selectArticles(Map<String, Object> paramMap){
		return sqlSession.selectList("boardMapper.articles", paramMap);
	}
	
	public List<Article> selectAccountArticles(int id){
		return sqlSession.selectList("boardMapper.accountArticleList");
	}
	
	public Article selectArticle(int id) {
		return sqlSession.selectOne("boardMapper.article", id);
	}
	
}
