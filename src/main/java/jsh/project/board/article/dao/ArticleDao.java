package jsh.project.board.article.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import jsh.project.board.article.domain.Article;
import jsh.project.board.article.dto.request.RequestArticlesDto;
import jsh.project.board.article.dto.request.like.RequestLikeDto;
import jsh.project.board.article.dto.response.ResponseArticleDetailDto;
import jsh.project.board.article.dto.response.ResponseArticleDto;
import jsh.project.board.article.dto.response.ResponseArticleUpdateDto;

@Repository
public class ArticleDao {
	
	private SqlSession sqlSession;
	
	public ArticleDao(SqlSession sqlSession) {
		this.sqlSession = sqlSession;
	}
	
	public int selectNoticeTotalCount() {
		return sqlSession.selectOne("boardMapper.selectNoticeTotalCount");
	}
	
	public List<ResponseArticleDto> selectNoticeArticles(Map<String, Integer> paramMap){
		return sqlSession.selectList("boardMapper.selectNoticeArticles", paramMap);
	}
	
	public int selectTotalCount(RequestArticlesDto dto) {
		return sqlSession.selectOne("boardMapper.selectTotalCount", dto);
	}
	
	public List<ResponseArticleDto> selectArticles(RequestArticlesDto dto){
		return sqlSession.selectList("boardMapper.selectArticles", dto);
	}
	
	public void updateViewCount(int id) {
		sqlSession.update("articleMapper.updateViewCount", id);
	}
	
	public ResponseArticleDetailDto selectArticle(int id) {
		return sqlSession.selectOne("articleMapper.selectArticle", id);
	}
	
	public ResponseArticleUpdateDto selectUpdateArticle(int id) {
		return sqlSession.selectOne("articleMapper.selectUpdateArticle",id);
	}
	
	public void insertArticle(Article article) {
		sqlSession.insert("articleMapper.insertArticle", article);
	}
	
	public void updateArticle(Article article) {
		sqlSession.update("articleMapper.updateArticle", article);
	}
	
	public void deleteArticle(int id) {
		sqlSession.delete("articleMapper.deleteArticle", id);
	}
	
	public void deleteReplys(int articleId) {
		sqlSession.delete("replyMapper.deleteReplys", articleId);
	}
	
	public void deleteLikes(int articleId) {
		sqlSession.delete("articleLikeMapper.deleteLikes", articleId);
	}
	
	public int articleLikeCheck(RequestLikeDto dto) {
		return sqlSession.selectOne("articleLikeMapper.selectLikeCount", dto);
	}
	
	public void insertLike(RequestLikeDto dto) {
		sqlSession.insert("articleLikeMapper.insertLike", dto);
	}
	
	public void deleteLike(RequestLikeDto dto) {
		sqlSession.insert("articleLikeMapper.deleteLike", dto);
	}
	
	
	
	
	
}
