package jsh.project.board.article.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import jsh.project.board.article.dto.Article;
import jsh.project.board.article.dto.ArticleResponseDto;
import jsh.project.board.article.dto.RequestArticlesDto;

@Repository
public class ArticleDao {
	
	private SqlSession sqlSession;
	
	public ArticleDao(SqlSession sqlSession) {
		this.sqlSession = sqlSession;
	}
	
	public int getNoticeTotalCount() {
		return sqlSession.selectOne("boardMapper.noticeTotalCount");
	}
	
	public List<ArticleResponseDto> selectNoticeArticles(){
		return sqlSession.selectList("boardMapper.noticeArticles");
	}
	
	public int getTotalCount(RequestArticlesDto dto) {
		return sqlSession.selectOne("boardMapper.totalCount", dto);
	}
	
	public List<ArticleResponseDto> selectArticles(RequestArticlesDto dto){
		return sqlSession.selectList("boardMapper.articles", dto);
	}
	
	public List<Article> selectAccountArticles(int id){
		return sqlSession.selectList("boardMapper.accountArticleList");
	}
	
	public Article selectArticle(int id) {
		return sqlSession.selectOne("boardMapper.article", id);
	}
	
}
