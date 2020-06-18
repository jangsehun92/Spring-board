package jsh.project.board.article;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.ArrayList;
import java.util.Date;
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
import jsh.project.board.article.dto.request.RequestArticleDetailDto;
import jsh.project.board.article.dto.request.RequestArticlesDto;
import jsh.project.board.article.dto.response.ResponseArticleDetailDto;
import jsh.project.board.article.dto.response.ResponseArticleDto;
import jsh.project.board.article.dto.response.ResponseBoardDto;
import jsh.project.board.article.enums.AdminCategory;
import jsh.project.board.article.enums.UserCategory;
import jsh.project.board.article.service.ArticleServiceImpl;
import jsh.project.board.global.infra.util.Pagination;

@RunWith(MockitoJUnitRunner.class)
public class ArticleServiceTest {
	private static final Logger log = LoggerFactory.getLogger(ArticleServiceTest.class);
	
	@Mock
	private ArticleDao articleDao;
	
	@InjectMocks
	private ArticleServiceImpl ArticleService;
	
	@Spy
	private List<ResponseArticleDto> articles = new ArrayList<ResponseArticleDto>();
	
	@Spy
	private List<ResponseArticleDto> noticeArticles = new ArrayList<ResponseArticleDto>();
	
	@Before
	public void setUp() {
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
		log.info(requestDto.toString());
		
		//Pagination객체를 만들 때, 필수 공지사항의 갯수만큼 표시할 일반 게시글의 수가 줄어 들어야한다.(ex: 필수 게시글이 3개이면 일반 게시글 7개로 총 10개 표시)
		Pagination pagination = new Pagination(articleDao.selectTotalCount(requestDto), requestDto.getPage(),articleDao.selectNoticeTotalCount());
		//게시글 리스트를 요청하는 DTO에 쿼리내에서 사용할 가져올 게시글의 범위를 셋팅해준다.
		requestDto.setStartCount(pagination.getStartCount());
		requestDto.setEndCount(pagination.getEndCount());
		log.info(pagination.toString());
		
		//response할 전체 articles를 선언하고
		List<ResponseArticleDto> selectArticles = new ArrayList<>();
		//Pagination객체에서 Dao에서 필수 공지사항 게시글 범위만큼 가져와 addAll 한다.
		selectArticles.addAll(articleDao.selectNoticeArticles(pagination.getNoticeScope()));
		//requestDto에 셋팅한 일반 게시글 범위만큼 가져와 addAll 한다. 
		selectArticles.addAll(articleDao.selectArticles(requestDto));
		
		//requestDto에서 ResponseDto 객체를 생성하여 가져온다.
		ResponseBoardDto responseBoardDto = requestDto.toResponseDto();
		//articles을 setter를 통해 넣는다.
		responseBoardDto.setArticles(selectArticles);
		//pagination객체를 responseDto에 setter를 통해 넣는다.
		responseBoardDto.setPagination(pagination);
		
		//then
		verify(articleDao, times(1)).selectNoticeTotalCount();
		verify(articleDao, times(1)).selectTotalCount(requestDto);
		verify(articleDao, times(1)).selectNoticeArticles(pagination.getNoticeScope());
		verify(articleDao, times(1)).selectArticles(requestDto);
		
		assertThat(responseBoardDto.getArticles(), is(selectArticles));
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
		//request 객체 생성
		RequestArticleDetailDto dto = new RequestArticleDetailDto();
		dto.setId(1);
		//login하였다면 SecurityContextHolder에서 principal객체 내에 있는 ID값을 넣어준다.
		dto.setAccountId(0);
		
		//given으로 돌려줄 ResponseArticleDetailDto
		ResponseArticleDetailDto articleDetailDto = new ResponseArticleDetailDto();
		articleDetailDto.setId(1);
		articleDetailDto.setAccountId(1);
		articleDetailDto.setCategory(AdminCategory.NOTICE.getValue());
		articleDetailDto.setNickname("tester");
		articleDetailDto.setTitle("testTitle");
		articleDetailDto.setContent("testContent");
		articleDetailDto.setViewCount(0);
		articleDetailDto.setLikeCount(1);
		articleDetailDto.setReplyCount(0);
		articleDetailDto.setRegdate(new Date());
		
		given(articleDao.selectArticle(dto.getId())).willReturn(articleDetailDto);
		given(articleDao.articleLikeCheck(any())).willReturn(1);
		
		//when
		ResponseArticleDetailDto responseDto =  ArticleService.getArticle(dto);
		
		//then
		verify(articleDao, times(1)).updateViewCount(dto.getId());
		verify(articleDao, times(1)).selectArticle(dto.getId());
		verify(articleDao, times(1)).articleLikeCheck(any());
		
		assertThat(responseDto.getId(), is(1));
		assertThat(responseDto.getAccountId(), is(1));
		assertThat(responseDto.getCategory(), is("notice"));
		assertThat(responseDto.getLikeCheck(), is(true));
		assertThat(responseDto.getLikeCount(), is(1));
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
		ArticleService.createArticle(dto);
		
		//then
		verify(articleDao, times(1)).insertArticle(any());
	}
	

}
