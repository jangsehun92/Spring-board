package jsh.project.board.article.service;

import org.springframework.web.multipart.MultipartFile;

import jsh.project.board.article.dto.request.RequestArticleCreateDto;
import jsh.project.board.article.dto.request.RequestArticleDetailDto;
import jsh.project.board.article.dto.request.RequestArticleUpdateDto;
import jsh.project.board.article.dto.request.RequestArticlesDto;
import jsh.project.board.article.dto.request.like.RequestLikeDto;
import jsh.project.board.article.dto.response.ResponseArticleDetailDto;
import jsh.project.board.article.dto.response.ResponseArticleUpdateDto;
import jsh.project.board.article.dto.response.ResponseBoardDto;

public interface ArticleService {
	
	public ResponseBoardDto getNoticeArticles(RequestArticlesDto dto);
	public ResponseBoardDto getArticles(RequestArticlesDto dto);
	public ResponseBoardDto getAccountArticles(RequestArticlesDto dto);
	public ResponseArticleDetailDto getArticle(RequestArticleDetailDto dto);
	public int createArticle(RequestArticleCreateDto dto);
	public ResponseArticleUpdateDto getUpdateArticle(int id);
	public void updateArticle(RequestArticleUpdateDto dto);
	public void deleteArticle(int id);
	public void like(RequestLikeDto dto);
	public String uploadFile(MultipartFile file);

}
