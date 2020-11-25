package jsh.project.board.article.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import jsh.project.board.article.domain.Article;
import jsh.project.board.article.dto.request.article.RequestArticlesDto;
import jsh.project.board.article.dto.request.like.RequestLikeDto;
import jsh.project.board.article.dto.response.ResponseArticleDetailDto;
import jsh.project.board.article.dto.response.ResponseArticleDto;
import jsh.project.board.article.dto.response.ResponseArticleUpdateDto;

@Repository
public class ArticleDaoImpl implements ArticleDao{
	
	private final SqlSession sqlSession;
	
	public ArticleDaoImpl(SqlSession sqlSession) {
		this.sqlSession = sqlSession;
	}
	
	@Override
	public int selectNoticeTotalCount() {
		return sqlSession.selectOne("boardMapper.selectNoticeTotalCount");
	}
	
	@Override
	public List<ResponseArticleDto> selectNoticeArticles(Map<String, Integer> paramMap){
		return sqlSession.selectList("boardMapper.selectNoticeArticles", paramMap);
	}
	
	@Override
	public int selectTotalCount(RequestArticlesDto dto) {
		return sqlSession.selectOne("boardMapper.selectTotalCount", dto);
	}
	
	@Override
	public List<ResponseArticleDto> selectArticles(RequestArticlesDto dto){
		return sqlSession.selectList("boardMapper.selectArticles", dto);
	}
	
	@Override
	public void updateViewCount(final int id) {
		sqlSession.update("articleMapper.updateViewCount", id);
	}
	
	@Override
	public ResponseArticleDetailDto selectArticle(final int id) {
		return sqlSession.selectOne("articleMapper.selectArticle", id);
	}
	
	@Override
	public ResponseArticleUpdateDto selectUpdateArticle(final int id) {
		return sqlSession.selectOne("articleMapper.selectUpdateArticle",id);
	}
	
	@Override
	public void insertArticle(final Article article) {
		sqlSession.insert("articleMapper.insertArticle", article);
	}
	
	@Override
	public void updateArticle(final Article article) {
		sqlSession.update("articleMapper.updateArticle", article);
	}
	
	@Override
	public void deleteArticle(final int id) {
		sqlSession.delete("articleMapper.deleteArticle", id);
	}
	
	@Override
	public void deleteReplys(final int articleId) {
		sqlSession.delete("replyMapper.deleteReplys", articleId);
	}
	
	@Override
	public void deleteLikes(final int articleId) {
		sqlSession.delete("articleLikeMapper.deleteLikes", articleId);
	}
	
	@Override
	public int selectArticleLikeCheck(RequestLikeDto dto) {
		return sqlSession.selectOne("articleLikeMapper.selectLikeCount", dto);
	}
	
	@Override
	public int selectArticleCheck(final int id) {
		return sqlSession.selectOne("articleMapper.selectArticleCheck",id);
		
	}
	
	@Override
	public void insertLike(RequestLikeDto dto) {
		sqlSession.insert("articleLikeMapper.insertLike", dto);
	}
	
	@Override
	public void deleteLike(RequestLikeDto dto) {
		sqlSession.insert("articleLikeMapper.deleteLike", dto);
	}
}
