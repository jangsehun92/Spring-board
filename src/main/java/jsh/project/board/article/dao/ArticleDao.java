package jsh.project.board.article.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import jsh.project.board.article.dto.ArticleResponseDto;
import jsh.project.board.article.dto.RequestArticlesDto;
import jsh.project.board.article.dto.ResponseArticleDetialDto;

@Repository
public class ArticleDao {
	
	private SqlSession sqlSession;
	
	public ArticleDao(SqlSession sqlSession) {
		this.sqlSession = sqlSession;
	}
	
	public int getNoticeTotalCount() {
		return sqlSession.selectOne("boardMapper.noticeTotalCount");
	}
	
	public List<ArticleResponseDto> selectNoticeArticles(Map<String, Integer> paramMap){
		return sqlSession.selectList("boardMapper.noticeArticles", paramMap);
	}
	
	public int getTotalCount(RequestArticlesDto dto) {
		return sqlSession.selectOne("boardMapper.totalCount", dto);
	}
	
	public List<ArticleResponseDto> selectArticles(RequestArticlesDto dto){
		return sqlSession.selectList("boardMapper.articles", dto);
	}
	
	public ResponseArticleDetialDto selectArticle(int id) {
		return sqlSession.selectOne("boardMapper.article", id);
	}
	
}
