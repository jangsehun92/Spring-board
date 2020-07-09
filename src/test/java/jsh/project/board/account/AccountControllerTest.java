package jsh.project.board.account;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.forwardedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
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

import jsh.project.board.account.controller.AccountController;
import jsh.project.board.account.service.AuthServiceImpl;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"file:src/main/webapp/WEB-INF/spring/appServlet/servlet-context.xml",
	"file:src/main/webapp/WEB-INF/spring/root-contextTest.xml",
	"file:src/main/webapp/WEB-INF/spring/security-context.xml"})
@WebAppConfiguration
@Transactional(transactionManager = "transactionManager")
public class AccountControllerTest{
	
	@Autowired
	private WebApplicationContext wac;
	
	private MockMvc mockMvc;
	
	@InjectMocks
	AccountController accountController;
	
	@Mock
	AuthServiceImpl authService;
	
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
	public void 로그인_페이지_요청() throws Exception{
		//given
		String url = "/login";
		
		//when
		ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.get(url));
		
		//then
		resultActions
			.andExpect(status().is(200))
			.andExpect(forwardedUrl("/WEB-INF/views/userPages/login.jsp"));
	}
	
	@Test
	public void 회원가입_페이지_요청() throws Exception{
		//given
		String url = "/account/join";
		
		//when
		ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.get(url));
		
		//then
		resultActions
			.andExpect(status().is(200))
			.andExpect(forwardedUrl("/WEB-INF/views/userPages/join.jsp"));
	}
	
	@Test
	public void 회원가입() throws Exception{
		//given
		String url = "/account/join";
		Map<String, String> resultMap = new HashMap<String, String>();
		resultMap.put("email", "jangsehun19@gmail.com");
		resultMap.put("password", "password");
		resultMap.put("passwordCheck", "password");
		resultMap.put("name", "장세훈");
		resultMap.put("birth", "920409");
		resultMap.put("nickname","tester");
		
		String content = objectMapper.writer().writeValueAsString(resultMap);
		
		//when
		ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders
				.post(url)
				.contentType(contentType)
				.content(content));
		
		//then
		resultActions
			.andExpect(status().is(200));
	}
	
	@Test
	public void 인증_이메일_재발송() throws Exception{
		//given
		String url = "/account/resend?email=jangsehun19@gmail.com";
		
		//when
		ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders
				.get(url));
		
		//then
		resultActions
			.andExpect(status().is(200));
	}
	
	@Test
	public void 유저_정보_페이지_요청()throws Exception {
		//given
		String url = "/account/info/21";
		
		//when
		ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders
				.get(url));
		
		//then
		resultActions
			.andExpect(status().is(200))
			.andExpect(forwardedUrl("/WEB-INF/views/userPages/info.jsp"));
	}
	
	@Test
	public void 회원_정보_수정_페이지_요청() throws Exception{
		//given
		String url = "/account/edit";
		
		//when
		ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders
					.get(url));
		
		//then
		resultActions
			.andExpect(status().is(200))
			.andExpect(forwardedUrl("/WEB-INF/views/userPages/edit.jsp"));
	}
	
	//security 관련 설정 후 다시 시도
	@Test
	public void 회원_정보_수정() throws Exception{
		//given
		String url = "/account/21";
		Map<String, String> resultMap = new HashMap<String, String>();
		resultMap.put("nickname","tester");
		
		String content = objectMapper.writer().writeValueAsString(resultMap);
		
		//when
		ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders
				.patch(url)
				.contentType(contentType)
				.content(content));
		
		//then
		resultActions
			.andExpect(status().is(200));
	}
	
	@Test
	public void 이메일_중복_체크() throws Exception{
		//given
		String url = "/account/email?email=jangsehun19192@gmail.com";
		
		//when
		ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders
				.get(url));
		
		//then
		resultActions
			.andExpect(status().is(200));
	}
	
	@Test
	public void 이메일_중복_체크시_중복된_경우() throws Exception{
		//given
		String url = "/account/email?email=jangsehun1992@gmail.com";
		
		//when
		ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders
				.get(url));
		
		//then
		resultActions
			.andExpect(status().is(302));
	}
	
	@Test
	public void 계정_찾기_페이지_요청() throws Exception{
		//given
		String url = "/account/find-email";
		
		//when
		ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders
				.get(url));
		
		//then
		resultActions
			.andExpect(status().is(200))
			.andExpect(forwardedUrl("/WEB-INF/views/userPages/findAccountPage.jsp"));
	}
	
	@Test
	public void 계정_찾기() throws Exception{
		//given
		String url = "/account/find-email";
		
		Map<String, String> resultMap = new HashMap<String, String>();
		resultMap.put("name","장세훈");
		resultMap.put("birth", "920409");
		
		String content = objectMapper.writer().writeValueAsString(resultMap);
		
		//when
		ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders
				.post(url)
				.contentType(contentType)
				.content(content));
		//then
		resultActions
			.andExpect(status().is(200));
	}
	
	@Test
	public void 비밀번호_찾기_페이지_요청() throws Exception{
		//given
		String url = "/account/find-password";
		
		//when
		ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders
				.get(url));
		
		//then
		resultActions
			.andExpect(status().is(200))
			.andExpect(forwardedUrl("/WEB-INF/views/userPages/findPasswordPage.jsp"));
	}
	
	@Test
	public void 비밀번호_찾기_이메일_발송() throws Exception{
		//given
		String url = "/account/reset";
		
		Map<String, String> resultMap = new HashMap<String, String>();
		resultMap.put("email","jangsehun1992@gmail.com");
		resultMap.put("name","장세훈");
		resultMap.put("birth", "920409");
		
		String content = objectMapper.writer().writeValueAsString(resultMap);
		
		//when
		ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders
				.post(url)
				.contentType(contentType)
				.content(content));
		//then
		resultActions
			.andExpect(status().is(200));
	}
	
	
	
	
	

}
