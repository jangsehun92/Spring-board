package jsh.project.board.article.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import jsh.project.board.article.domain.Article;
import jsh.project.board.article.domain.Like;
import jsh.project.board.article.dto.request.article.RequestArticlesDto;
import jsh.project.board.article.dto.response.ResponseArticleDetailDto;
import jsh.project.board.article.dto.response.ResponseArticleDto;

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
	public List<ResponseArticleDto> selectNoticeArticles(final Map<String, Integer> paramMap){
		return sqlSession.selectList("boardMapper.selectNoticeArticles", paramMap);
	}
	
	@Override
	public int selectTotalCount(final RequestArticlesDto dto) {
		return sqlSession.selectOne("boardMapper.selectTotalCount", dto);
	}
	
	@Override
	public List<ResponseArticleDto> selectArticles(final RequestArticlesDto dto){
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
	public Article selectUpdateArticle(final int id) {
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
	public int selectArticleLikeCheck(final Like like) {
		return sqlSession.selectOne("articleLikeMapper.selectLikeCount", like);
	}
	
	@Override
	public int selectArticleCheck(final int id) {
		return sqlSession.selectOne("articleMapper.selectArticleCheck",id);
		
	}
	
	@Override
	public void insertLike(final Like like) {
		sqlSession.insert("articleLikeMapper.insertLike", like);
	}
	
	@Override
	public void deleteLike(final Like like) {
		sqlSession.insert("articleLikeMapper.deleteLike", like);
	}
}
