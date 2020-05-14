package jsh.project.board.article.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import jsh.project.board.article.dto.Article;

@Repository
public class ArticleDao {
	
	private SqlSession sqlSession;
	
	public ArticleDao(SqlSession sqlSession) {
		this.sqlSession = sqlSession;
	}
	
	public List<Article>selectArticleList(){
		return sqlSession.selectList("boardMapper.articleList");
	}
	
	public List<Article>selectAccountArticleList(int id){
		return sqlSession.selectList("boardMapper.accountArticleList");
	}
	
	public Article selectArticle(int id) {
		return sqlSession.selectOne("boardMapper.article", id);
	}
	
}
