package jsh.project.board.article.dao;

import java.util.List;
import java.util.Map;

import jsh.project.board.article.domain.Article;
import jsh.project.board.article.dto.request.article.RequestArticlesDto;
import jsh.project.board.article.dto.request.like.RequestLikeDto;
import jsh.project.board.article.dto.response.ResponseArticleDetailDto;
import jsh.project.board.article.dto.response.ResponseArticleDto;
import jsh.project.board.article.dto.response.ResponseArticleUpdateDto;

public interface ArticleDao {
	public int selectNoticeTotalCount();
	public List<ResponseArticleDto> selectNoticeArticles(Map<String, Integer> paramMap);
	public int selectTotalCount(RequestArticlesDto dto);
	public List<ResponseArticleDto> selectArticles(RequestArticlesDto dto);
	public void updateViewCount(final int id);
	public ResponseArticleDetailDto selectArticle(int id);
	public ResponseArticleUpdateDto selectUpdateArticle(int id);
	public void insertArticle(final Article article);
	public void updateArticle(final Article article);
	public void deleteArticle(int id);
	public void deleteReplys(final int articleId);
	public void deleteLikes(final int articleId);
	public int selectArticleLikeCheck(RequestLikeDto dto);
	public int selectArticleCheck(final int id);
	public void insertLike(RequestLikeDto dto);
	public void deleteLike(RequestLikeDto dto);
}
