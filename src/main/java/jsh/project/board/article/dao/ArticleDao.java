package jsh.project.board.article.dao;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import jsh.project.board.article.dto.Article;

@Repository
public class ArticleDao {
	
	private SqlSession sqlSession;
	
	public ArticleDao(SqlSession sqiSession) {
		this.sqlSession = sqlSession;
	}
	
	public Article getArticle(int id) {
		return sqlSession.selectOne("boardMapper.selectOne", id);
	}

}
