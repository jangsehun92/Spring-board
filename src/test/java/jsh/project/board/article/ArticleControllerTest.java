package jsh.project.board.article;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.forwardedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;

import com.fasterxml.jackson.databind.ObjectMapper;

import jsh.project.board.article.controller.ArticleController;
import jsh.project.board.article.dto.request.like.RequestLikeDto;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"file:src/main/webapp/WEB-INF/spring/appServlet/servlet-context.xml",
	"file:src/main/webapp/WEB-INF/spring/root-contextTest.xml",
	"file:src/main/webapp/WEB-INF/spring/security-context.xml"})
@WebAppConfiguration
@Transactional(transactionManager = "transactionManager")
public class ArticleControllerTest{
	
	@Autowired
	private WebApplicationContext wac;
	
	private MockMvc mockMvc;
	
	@InjectMocks
	ArticleController articleController;
	
	private ObjectMapper objectMapper = new ObjectMapper();
	
	public final String contentType = "application/json; charset=UTF-8";
	
	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		this.mockMvc = MockMvcBuilders
					.webAppContextSetup(wac)
					.addFilters(new CharacterEncodingFilter("UTF-8", true))
					.alwaysDo(print())
					.build();
	}
	
	@Test
	public void 공지사항_게시글_가져오기() throws Exception{
		//given
		String category = "notice";
		
		//when
		ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders
				.get("/articles/"+category));
		
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
		ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders
				.get("/articles/"+category));
		
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
		ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders
				.get("/articles/account/"+accountId));
		
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
		ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders
				.get("/article/"+articleId));
		
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
		ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders
				.post("/article/like/177")
				.contentType(contentType)
				.content(content));
		
		//then
		resultActions.andExpect(status().isOk());
	}
	
	@Test
	public void 일반_게시글_작성_요청() throws Exception {
		//given
		String category = "community";
		
		//when
		ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders
				.get("/articles/"+category+"/create"));

		//then
		resultActions
			.andExpect(status().isOk())
			.andExpect(model().attributeExists("category"))
			.andExpect(model().attributeExists("categorys"))
			.andExpect(model().attribute("category", "COMMUNITY"))
			.andExpect(forwardedUrl("/WEB-INF/views/articlePages/articleCreatePage.jsp"));
	}
	
	@Test
	public void 관리자_게시글_작성_요청() throws Exception {
		//given
		String category = "notice";
		
		//when
		ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders
				.get("/admin/articles/"+category+"/create"));

		//then
		resultActions
			.andExpect(status().isOk())
			.andExpect(model().attributeExists("category"))
			.andExpect(model().attributeExists("categorys"))
			.andExpect(model().attributeExists("articleImportance"))
			.andExpect(model().attribute("category", "NOTICE"))
			.andExpect(forwardedUrl("/WEB-INF/views/articlePages/articleCreatePage.jsp"));
	}
	
	@Test
	public void 글_작성() throws Exception {
		//given
		Map<String, String> resultMap = new HashMap<String, String>();
		resultMap.put("accountId", "21");
		resultMap.put("importance", "일반");
		resultMap.put("category", "community");
		resultMap.put("title", "test");
		resultMap.put("content", "test");
		
		String content = objectMapper.writer().writeValueAsString(resultMap);
		
		//when
		ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders
				.post("/article")
				.contentType(contentType)
				.content(content));
		
		//then
		resultActions.andExpect(status().isOk());
	}
	
	@Test
	public void 글_수정() throws Exception {
		//given
		int id = 177;
		
		Map<String, String> resultMap = new HashMap<String, String>();
		resultMap.put("id", "177");
		resultMap.put("accountId", "21");
		resultMap.put("importance", "일반");
		resultMap.put("category", "question");
		resultMap.put("title", "제목 수정");
		resultMap.put("content", "내용 수정");
		
		String content = objectMapper.writer().writeValueAsString(resultMap);
		
		//when
		ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders
				.patch("/article/"+id)
				.contentType(contentType)
				.content(content));
		
		//then
		resultActions.andExpect(status().isOk());
	}
	
	@Test
	public void 글_삭제() throws Exception {
		//given
		int id = 177;
		
		Map<String, String> resultMap = new HashMap<String, String>();
		resultMap.put("articleId", "177");
		resultMap.put("accountId", "21");
		
		String content = objectMapper.writer().writeValueAsString(resultMap);
		
		//when
		ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders
				.delete("/article/"+id)
				.contentType(contentType)
				.content(content));
		//then
		resultActions.andExpect(status().isOk());
	}
	
	@Test
	public void 파일_업로드() throws Exception {
		//given
		MockMultipartFile file = new MockMultipartFile("file", "testFile.txt", "text/plain", "test".getBytes());
		
		//when
		ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders
				.multipart("/article/image")
				.file(file));
		
		//then
		resultActions.andExpect(status().isOk());
	}
	

}
