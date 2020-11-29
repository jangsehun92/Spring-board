package jsh.project.board.article.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import jsh.project.board.article.dao.ArticleDao;
import jsh.project.board.article.domain.Article;
import jsh.project.board.article.domain.Like;
import jsh.project.board.article.dto.request.article.RequestArticleCreateDto;
import jsh.project.board.article.dto.request.article.RequestArticleDeleteDto;
import jsh.project.board.article.dto.request.article.RequestArticleDetailDto;
import jsh.project.board.article.dto.request.article.RequestArticleUpdateDto;
import jsh.project.board.article.dto.request.article.RequestArticlesDto;
import jsh.project.board.article.dto.request.like.RequestLikeDto;
import jsh.project.board.article.dto.response.ResponseArticleDetailDto;
import jsh.project.board.article.dto.response.ResponseArticleDto;
import jsh.project.board.article.dto.response.ResponseArticleUpdateDto;
import jsh.project.board.article.dto.response.ResponseBoardDto;
import jsh.project.board.article.exception.ArticleNotFoundException;
import jsh.project.board.article.exception.ArticlesNotFoundException;
import jsh.project.board.global.infra.util.FileService;
import jsh.project.board.global.infra.util.Pagination;

@Service
public class ArticleServiceImpl implements ArticleService{
	private static final Logger log = LoggerFactory.getLogger(ArticleServiceImpl.class);
	
	private final ArticleDao articleDao;
	private final FileService fileService;
	
	public ArticleServiceImpl(ArticleDao articleDao, FileService fileService) {
		this.articleDao = articleDao;
		this.fileService = fileService;
	}
	
	@Override
	public ResponseBoardDto getNoticeArticles(RequestArticlesDto dto){
		log.info(dto.toString());
		
		int totalCount = articleDao.selectTotalCount(dto);
		int page = dto.getPage();
		final Pagination pagination = Pagination.of(totalCount, page);
		
		dto.setStartCount(pagination.getStartCount());
		dto.setEndCount(pagination.getEndCount());
		
		List<ResponseArticleDto> articles = articleDao.selectArticles(dto);
		
		final ResponseBoardDto responseArticles = ResponseBoardDto.of(articles, pagination, dto);
		return responseArticles;
	}
	
	@Override
	public ResponseBoardDto getArticles(RequestArticlesDto dto){
		log.info(dto.toString());
		
		int totalCount = articleDao.selectTotalCount(dto);
		int page = dto.getPage();
		int noticeTotalCount = articleDao.selectNoticeTotalCount();
		final Pagination pagination = Pagination.of(totalCount, page, noticeTotalCount);
		
		dto.setStartCount(pagination.getStartCount());
		dto.setEndCount(pagination.getEndCount());
		
		List<ResponseArticleDto> articles = new ArrayList<>();
		articles.addAll(articleDao.selectNoticeArticles(pagination.getNoticeScope()));
		articles.addAll(articleDao.selectArticles(dto));
		
		final ResponseBoardDto responseArticles = ResponseBoardDto.of(articles, pagination, dto);
		return responseArticles;
	}
	
	@Override
	public ResponseBoardDto getAccountArticles(RequestArticlesDto dto){
		log.info(dto.toString());
		
		int totalCount = articleDao.selectTotalCount(dto);
		int page = dto.getPage();
		final Pagination pagination = Pagination.of(totalCount, page);
		
		dto.setStartCount(pagination.getStartCount());
		dto.setEndCount(pagination.getEndCount());
		
		List<ResponseArticleDto> articles = articleDao.selectArticles(dto);
		if(articles.isEmpty()) throw new ArticlesNotFoundException();
		
		final ResponseBoardDto responseArticles = ResponseBoardDto.of(articles, pagination, dto);
		return responseArticles;
	}
	
	@Override
	public ResponseArticleDetailDto getArticle(final RequestArticleDetailDto dto) {
		log.info(dto.toString());
		final ResponseArticleDetailDto responseArticleDetailDto = articleDao.selectArticle(dto.getId());
		if(responseArticleDetailDto == null) throw new ArticleNotFoundException();
		
		articleDao.updateViewCount(dto.getId());
		
		final Like like = Like.of(dto.getId(), dto.getAccountId());
		responseArticleDetailDto.setLikeCheck(articleDao.selectArticleLikeCheck(like));
		log.info(responseArticleDetailDto.toString());
		return responseArticleDetailDto;
	}
	
	@Transactional
	@Override
	public int createArticle(final RequestArticleCreateDto dto) {
		log.info(dto.toString());
		final Article article = Article.from(dto);
		articleDao.insertArticle(article);
		log.info(article.toString());
		return article.getId();
	}
	
	@Transactional
	@Override
	public ResponseArticleUpdateDto getUpdateArticle(final int id) {
		final Article article = articleDao.selectUpdateArticle(id);
		if(article == null) throw new ArticleNotFoundException();
		final ResponseArticleUpdateDto dto = ResponseArticleUpdateDto.from(article);
		log.info(dto.toString());
		return dto;
	}
	
	@Transactional
	@Override
	public void updateArticle(final RequestArticleUpdateDto dto) {
		log.info(dto.toString());
		Article article = articleDao.selectUpdateArticle(dto.getId());
		if(article == null) throw new ArticleNotFoundException();
		article.editArticle(dto);
		log.info(article.toString());
		articleDao.updateArticle(article);
	}
	
	@Transactional
	@Override
	public void deleteArticle(final RequestArticleDeleteDto dto) {
		log.info("deleteArticle : " + dto.getArticleId());
		if(articleDao.selectArticleCheck(dto.getArticleId()) == 0) throw new ArticleNotFoundException();
		articleDao.deleteArticle(dto.getArticleId());
		articleDao.deleteReplys(dto.getArticleId());
		articleDao.deleteLikes(dto.getArticleId());
	}
	
	@Transactional
	@Override
	public void like(final RequestLikeDto dto) {
		log.info(dto.toString());
		final Like like = Like.from(dto);
		if(articleDao.selectArticleCheck(dto.getArticleId()) == 0) throw new ArticleNotFoundException();
		if(articleDao.selectArticleLikeCheck(like) == 0) {
			articleDao.insertLike(like);
			log.info("insertLike");
		}else {
			articleDao.deleteLike(like);
			log.info("deleteLike");
		}
	}
	
	@Override
	public String uploadFile(final MultipartFile file) {
		log.info("uploadFileInfo { name : "+file.getOriginalFilename() + " size :  "+ file.getSize() + " type : " + file.getContentType() + " } ");
		return fileService.upload(file);
	}
	
}
