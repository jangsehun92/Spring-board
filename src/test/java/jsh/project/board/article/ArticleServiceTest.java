package jsh.project.board.article;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jsh.project.board.article.dao.ArticleDao;
import jsh.project.board.article.dto.request.RequestArticleCreateDto;
import jsh.project.board.article.dto.request.RequestArticleDeleteDto;
import jsh.project.board.article.dto.request.RequestArticleDetailDto;
import jsh.project.board.article.dto.request.RequestArticleUpdateDto;
import jsh.project.board.article.dto.request.RequestArticlesDto;
import jsh.project.board.article.dto.request.like.RequestLikeDto;
import jsh.project.board.article.dto.response.ResponseArticleDetailDto;
import jsh.project.board.article.dto.response.ResponseArticleDto;
import jsh.project.board.article.dto.response.ResponseArticleUpdateDto;
import jsh.project.board.article.dto.response.ResponseBoardDto;
import jsh.project.board.article.enums.AdminCategory;
import jsh.project.board.article.enums.UserCategory;
import jsh.project.board.article.service.ArticleServiceImpl;

@RunWith(MockitoJUnitRunner.class)
public class ArticleServiceTest {
	private static final Logger log = LoggerFactory.getLogger(ArticleServiceTest.class);
	
	@Mock
	private ArticleDao articleDao;
	
	@InjectMocks
	private ArticleServiceImpl articleService;
	
	@Mock
	private ResponseArticleDetailDto responseArticleDetailDto;
	
	@Spy
	private List<ResponseArticleDto> articles = new ArrayList<ResponseArticleDto>();
	
	@Spy
	private List<ResponseArticleDto> noticeArticles = new ArrayList<ResponseArticleDto>();
	
	@Before
	public void setUp() {
		setArticleList();
		setNoticeArticleListI();
		responseArticleDetailDto = new ResponseArticleDetailDto();
	}
	
	public void setArticleList() {
		log.info("setUp : articles");
		for(int i = 1; i <=10; i++) {
			ResponseArticleDto responseArticleDto = new ResponseArticleDto();
			responseArticleDto.setId(i);
			responseArticleDto.setCategory(UserCategory.COMMUNITY.getValue());
			responseArticleDto.setAccountId(1);
			responseArticleDto.setTitle("testTitle");
			responseArticleDto.setNickname("tester");
			responseArticleDto.setLikeCount(0);
			responseArticleDto.setReplyCount(0);
			responseArticleDto.setViewCount(0);
			articles.add(responseArticleDto);
		}
	}
	
	public void setNoticeArticleListI() {
		log.info("setUp : noticeArticles");
		for(int i = 1; i <=3; i++) {
			ResponseArticleDto responseArticleDto = new ResponseArticleDto();
			responseArticleDto.setId(i);
			responseArticleDto.setCategory(AdminCategory.NOTICE.getValue());
			responseArticleDto.setAccountId(1);
			responseArticleDto.setTitle("testNoticeTitle");
			responseArticleDto.setNickname("admin");
			responseArticleDto.setLikeCount(0);
			responseArticleDto.setReplyCount(0);
			responseArticleDto.setViewCount(0);
			articles.add(responseArticleDto);
		}
	}
	
	@Test
	public void 유저카테고리_게시판_모든_게시글_보기() {
		//given
		//request 객체 생성(커뮤니티 게시판 요청)
		String category = UserCategory.COMMUNITY.getValue();
		RequestArticlesDto requestDto = new RequestArticlesDto();
		requestDto.setCategory(category);
		requestDto.setAccountId(0);
		
		given(articleDao.selectNoticeArticles(any())).willReturn(noticeArticles);
		given(articleDao.selectArticles(requestDto)).willReturn(articles);
		given(articleDao.selectTotalCount(requestDto)).willReturn(100);
		given(articleDao.selectNoticeTotalCount()).willReturn(3);
		
		//when
		ResponseBoardDto responseBoardDto = articleService.getArticles(requestDto);
		
		//then
		verify(articleDao, times(1)).selectNoticeTotalCount();
		verify(articleDao, times(1)).selectTotalCount(requestDto);
		verify(articleDao, times(1)).selectNoticeArticles(any());
		verify(articleDao, times(1)).selectArticles(requestDto);
		
		/*
		 * given에서 필수 공지사항 게시글의 갯수를 3개로 리턴되게 해놨기때문에, 
		 * 필수 공지사항 게시글을 가져오는 범위는 1~3이고, 
		 * 일반 게시글은 한페이지당 표시되는 게시글은 7개, 
		 * 총 100개의 일반 게시글을 표시하려면 15페이지가 되어야한다. (100/7 = 14 이지만 나머지값이 존재 하기때문에 총 페이지 수 +1)
		 */
		assertThat(responseBoardDto.getPagination().getNoticeScope().get("startCount"), is(1));
		assertThat(responseBoardDto.getPagination().getNoticeScope().get("endCount"), is(3));
		//한페이지당 보여질 일반 게시글의 수
		assertThat(responseBoardDto.getPagination().getCountList(), is(7));
		//전체 페이지 수
		assertThat(responseBoardDto.getPagination().getTotalPage(), is(15));
		
	}
	
	@Test
	public void 단일_게시글_가져오기() {
		//given
		RequestArticleDetailDto dto = new RequestArticleDetailDto();
		dto.setId(1);
		//login하였다면 SecurityContextHolder에서 principal객체 내에 있는 ID값을 넣어준다.
		dto.setAccountId(0);
		
		given(articleDao.selectArticle(dto.getId())).willReturn(responseArticleDetailDto);
		
		//when
		ResponseArticleDetailDto responseDto =  articleService.getArticle(dto);
		
		//then
		verify(articleDao, times(1)).updateViewCount(dto.getId());
		verify(articleDao, times(1)).selectArticle(dto.getId());
		verify(articleDao, times(1)).articleLikeCheck(any());
		
		assertNotNull(responseDto);
	}
	
	@Test
	public void 게시글_입력() {
		//given
		RequestArticleCreateDto dto = new RequestArticleCreateDto();
		dto.setAccountId(1);
		dto.setCategory(AdminCategory.NOTICE.getValue());
		dto.setContent("testContent");
		dto.setImportance(1);
		dto.setTitle("중요한 공지사항");
		
		
		//when
		articleService.createArticle(dto);
		
		//then
		verify(articleDao, times(1)).insertArticle(any());
	}
	
	@Test
	public void 수정할_게시글_가져오기() {
		//given
		int articleId = 1;
		
		given(articleDao.selectUpdateArticle(articleId)).willReturn(new ResponseArticleUpdateDto());
		
		//when
		ResponseArticleUpdateDto responseDto = articleService.getUpdateArticle(articleId);
		
		//then
		verify(articleDao, times(1)).selectUpdateArticle(articleId);
		
		assertNotNull(responseDto);
	}
	
	@Test
	public void 게시글_수정() {
		//given
		RequestArticleUpdateDto dto = new RequestArticleUpdateDto();
		dto.setId(1);
		dto.setAccountId(1);
		dto.setCategory(AdminCategory.NOTICE.getValue());
		dto.setImportance(0);
		dto.setTitle("update title");
		dto.setContent("update content");
		
		//when
		articleService.updateArticle(dto);
		
		//then
		verify(articleDao, times(1)).updateArticle(any());
	}
	
	@Test
	public void 게시글_삭제() {
		//given
		RequestArticleDeleteDto dto = new RequestArticleDeleteDto();
		dto.setArticleId(1);
		dto.setAccountId(1);
		
		//when
		articleService.deleteArticle(dto.getArticleId());
		
		//then
		verify(articleDao, times(1)).deleteArticle(dto.getArticleId());
		verify(articleDao, times(1)).deleteReplys(dto.getArticleId());
		verify(articleDao, times(1)).deleteLikes(dto.getArticleId());
	}
	
	@Test
	public void 해당_게시글_좋아요() {
		//given
		RequestLikeDto dto = new RequestLikeDto();
		dto.setArticleId(1);
		dto.setAccountId(1);
		
		//when
		articleService.like(dto);
		
		//then
		verify(articleDao, times(1)).articleLikeCheck(dto);
	}
	

}
