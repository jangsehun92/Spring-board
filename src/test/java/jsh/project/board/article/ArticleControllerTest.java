package jsh.project.board.article;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.nio.charset.Charset;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.CsrfRequestPostProcessor;
import org.springframework.security.web.csrf.CsrfAuthenticationStrategy;
import org.hamcrest.core.IsNull;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.test.web.servlet.ResultActions;

import jsh.project.board.article.controller.ArticleController;
import jsh.project.board.article.dto.request.like.RequestLikeDto;
import jsh.project.board.article.service.ArticleServiceImpl;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"file:src/main/webapp/WEB-INF/spring/appServlet/servlet-context.xml",
	"file:src/main/webapp/WEB-INF/spring/root-contextTest.xml",
	"file:src/main/webapp/WEB-INF/spring/security-context.xml"})
@WebAppConfiguration
@Transactional(transactionManager = "transactionManager")
public class ArticleControllerTest implements SecurityContext {
	
	@Autowired
	private WebApplicationContext wac;
	
	private MockMvc mockMvc;
	
	@Mock
	ArticleServiceImpl articleService;
	
	@InjectMocks
	ArticleController articleController;
	
	private ObjectMapper objectMapper = new ObjectMapper();
	
	public String contentType = "application/json; charset=UTF-8";
	
	private Authentication authentication;
	
	public ArticleControllerTest(Authentication authentication) {
		this.authentication = authentication;
	}
	
	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		this.mockMvc = MockMvcBuilders.webAppContextSetup(wac).addFilters(new CharacterEncodingFilter("UTF-8", true)).alwaysDo(print()).build();
	}
	
	@Override
	public Authentication getAuthentication() {
		return this.authentication;
	}

	@Override
	public void setAuthentication(Authentication authentication) {
		this.authentication = authentication;
	}
	
	@Test
	public void 공지사항_게시글_가져오기() throws Exception{
		//given
		String category = "notice";
		
		//when
		ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.get("/articles/"+category));
		
		//then
		resultActions
			.andExpect(status().is(200))
			.andExpect(forwardedUrl("/WEB-INF/views/articlePages/articles.jsp"));
	}
	
	@Test
	public void 일반_게시글_가져오기() throws Exception{
		//given
		String category = "community";
		
		//when
		ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.get("/articles/"+category));
		
		//then
		resultActions
			.andExpect(status().is(200))
			.andExpect(forwardedUrl("/WEB-INF/views/articlePages/articles.jsp"));
	}
	
	@Test
	public void 해당_유저_글목록_보기()throws Exception {
		//given
		String accountId = "1";
		
		//when
		ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.get("/articles/account/"+accountId));
		
		//then
		resultActions
			.andExpect(status().is(200))
			.andExpect(jsonPath("articles[0].id").value(205));
	}
	
	@Test
	public void 단일_게시글_보기() throws Exception {
		//given
		String articleId = "177";
		
		//when
		ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.get("/article/"+articleId));
		
		//then
		resultActions
			.andExpect(status().is(200))
			.andExpect(forwardedUrl("/WEB-INF/views/articlePages/articleDetail.jsp"))
			.andExpect(model().attributeExists("responseDto"));
	}
	
	@Test
	public void 해당_게시글_좋아요() throws Exception {
		//given
		
		RequestLikeDto dto = new RequestLikeDto();
		dto.setAccountId(21);
		dto.setArticleId(177);
		String content = objectMapper.writer().writeValueAsString(dto);
		
		//when
		ResultActions resultActions = mockMvc.perform(post("/article/like/177").contentType(contentType).content(content));
		
		//then
		resultActions.andExpect(status().isOk());
		
	}
	

}
