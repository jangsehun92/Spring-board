package jsh.project.board.article.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import jsh.project.board.article.dto.ArticleResponseDto;
import jsh.project.board.article.dto.RequestArticleDeleteDto;
import jsh.project.board.article.dto.RequestArticlesDto;
import jsh.project.board.article.dto.ResponseArticleDetailDto;
import jsh.project.board.article.dto.like.RequestLikeDto;

@Repository
public class ArticleDao {
	
	private SqlSession sqlSession;
	
	public ArticleDao(SqlSession sqlSession) {
		this.sqlSession = sqlSession;
	}
	
	public int selectNoticeTotalCount() {
		return sqlSession.selectOne("boardMapper.noticeTotalCount");
	}
	
	public List<ArticleResponseDto> selectNoticeArticles(Map<String, Integer> paramMap){
		return sqlSession.selectList("boardMapper.noticeArticles", paramMap);
	}
	
	public int selectTotalCount(RequestArticlesDto dto) {
		return sqlSession.selectOne("boardMapper.totalCount", dto);
	}
	
	public List<ArticleResponseDto> selectArticles(RequestArticlesDto dto){
		return sqlSession.selectList("boardMapper.articles", dto);
	}
	
	public void updateViewCount(int id) {
		sqlSession.update("articleMapper.viewCount", id);
	}
	
	public ResponseArticleDetailDto selectArticle(int id) {
		return sqlSession.selectOne("articleMapper.article", id);
	}
	
	public void deleteArticle(RequestArticleDeleteDto dto) {
		sqlSession.delete("articleMapper.deleteArticle", dto);
	}
	
	public void deleteReplys(int articleId) {
		sqlSession.delete("replyMapper.deleteReplys", articleId);
	}
	
	public void deleteLikes(int articleId) {
		sqlSession.delete("articleLikeMapper.deleteLikes", articleId);
	}
	
	public int articleLikeCheck(RequestLikeDto dto) {
		return sqlSession.selectOne("articleLikeMapper.likeCheck", dto);
	}
	
	public void insertLike(RequestLikeDto dto) {
		sqlSession.insert("articleLikeMapper.like", dto);
	}
	
	public void deleteLike(RequestLikeDto dto) {
		sqlSession.insert("articleLikeMapper.likeCancel", dto);
	}
	
	
	
	
	
}
