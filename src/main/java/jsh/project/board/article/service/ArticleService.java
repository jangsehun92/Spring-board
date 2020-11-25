package jsh.project.board.article.service;

import org.springframework.web.multipart.MultipartFile;

import jsh.project.board.article.dto.request.article.RequestArticleCreateDto;
import jsh.project.board.article.dto.request.article.RequestArticleDeleteDto;
import jsh.project.board.article.dto.request.article.RequestArticleDetailDto;
import jsh.project.board.article.dto.request.article.RequestArticleUpdateDto;
import jsh.project.board.article.dto.request.article.RequestArticlesDto;
import jsh.project.board.article.dto.request.like.RequestLikeDto;
import jsh.project.board.article.dto.response.ResponseArticleDetailDto;
import jsh.project.board.article.dto.response.ResponseArticleUpdateDto;
import jsh.project.board.article.dto.response.ResponseBoardDto;

public interface ArticleService {
	public ResponseBoardDto getNoticeArticles(final RequestArticlesDto dto);
	public ResponseBoardDto getArticles(final RequestArticlesDto dto);
	public ResponseBoardDto getAccountArticles(final RequestArticlesDto dto);
	public ResponseArticleDetailDto getArticle(final RequestArticleDetailDto dto);
	public int createArticle(final RequestArticleCreateDto dto);
	public ResponseArticleUpdateDto getUpdateArticle(final int id);
	public void updateArticle(final RequestArticleUpdateDto dto);
	public void deleteArticle(final RequestArticleDeleteDto dto);
	public void like(final RequestLikeDto dto);
	public String uploadFile(final MultipartFile file);
}
