package jsh.project.board.reply;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
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

import jsh.project.board.reply.controller.ReplyController;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"file:src/main/webapp/WEB-INF/spring/appServlet/servlet-context.xml",
	"file:src/main/webapp/WEB-INF/spring/root-contextTest.xml",
	"file:src/main/webapp/WEB-INF/spring/security-context.xml"})
@WebAppConfiguration
@Transactional(transactionManager = "transactionManager")
public class ReplyControllerTest{
	
	@Autowired
	private WebApplicationContext wac;
	
	private MockMvc mockMvc;
	
	@InjectMocks
	ReplyController replyController;
	
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
	public void 해당_게시글_전체_댓글_가져오기() throws Exception{
		//given
		int articleId = 177;
		
		//when
		ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.get("/replys/"+articleId));
		
		//then
		resultActions
			.andExpect(status().is(200));
	}
	
	@Test
	public void 댓글_입력() throws Exception{
		//given
		Map<String, String> resultMap = new HashMap<String, String>();
		resultMap.put("articleId","177");
		resultMap.put("accountId", "21");
		resultMap.put("content", "댓글 작성");
		
		String content = objectMapper.writer().writeValueAsString(resultMap);
		
		//when
		ResultActions resultActions = mockMvc
				.perform(post("/reply")
				.contentType(contentType)
				.content(content));
		
		//then
		resultActions
			.andExpect(status().isOk());
	}
	
	@Test
	public void 댓글_수정() throws Exception {
		//given
		int id = 61;
		
		Map<String, String> resultMap = new HashMap<String, String>();
		resultMap.put("id", "61");
		resultMap.put("articleId","177");
		resultMap.put("accountId", "21");
		resultMap.put("content", "댓글 수정");
		
		String content = objectMapper.writer().writeValueAsString(resultMap);
		
		//when
		ResultActions resultActions = mockMvc
				.perform(MockMvcRequestBuilders.patch("/reply/"+id)
				.contentType(contentType)
				.content(content));
		
		//then
		resultActions
			.andExpect(status().isOk());
	}
	
	@Test
	public void 댓글_삭제() throws Exception {
		//given
		int id = 61;
		
		Map<String, String> resultMap = new HashMap<String, String>();
		resultMap.put("id", "61");
		resultMap.put("articleId", "177");
		resultMap.put("accountId", "21");
		
		String content = objectMapper.writer().writeValueAsString(resultMap);
		
		//when
		ResultActions resultActions = mockMvc
				.perform(MockMvcRequestBuilders.delete("/reply/"+id)
				.contentType(contentType)
				.content(content));
		
		//then
		resultActions
			.andExpect(status().isOk());
	}

}
