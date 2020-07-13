package jsh.project.board.account.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;

import jsh.project.board.account.service.AccountService;

public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler{
	//private static final org.slf4j.Logger log = LoggerFactory.getLogger(CustomAuthenticationSuccessHandler.class);
	//spring security 에서 제공하는 사용자 요청을 저장하고 꺼낼 수 있는 객체
	private RequestCache requestCache = new HttpSessionRequestCache();
	private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();
	
	//login에 성공한 email
	private String email;
	//login 성공시 redirect 해줄 default url
	private String defaultUrl;
	
	@Autowired
	private AccountService accountService;

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
		email = request.getParameter("email");
		accountService.updateLoginDate(email);
		accountService.updateFailureCount(email, 0);
		resultRedirectStrategy(request, response, authentication);
		clearAuthenticationAttributes(request);
	}
	
	//login 성공시 Redirect할 Url 결정
	protected void resultRedirectStrategy(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
		//login View로 전환되기 전 url의 값을 가져온다.(권한이 필요한 요청을 했을 때, 로그인이 되어있지 않으면 로그인 View로 가기 때문이다)
		SavedRequest savedRequest = requestCache.getRequest(request, response);
		
		if(savedRequest != null) {
			String targetUrl = savedRequest.getRedirectUrl();
			redirectStrategy.sendRedirect(request, response, targetUrl);
		}else {
			redirectStrategy.sendRedirect(request, response, defaultUrl);
		}
	}
	
	//login 실패 에러 세션 지우기(로그인이 한번이라도 실패하면 관련 에러가 세션에 남아 있다.)
	protected void clearAuthenticationAttributes(HttpServletRequest request) {
		HttpSession session = request.getSession(false);
		if(session == null) {
			return;
		}
		session.removeAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
	}
	
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getDefaultUrl() {
		return defaultUrl;
	}
	
	public void setDefaultUrl(String defaultUrl) {
		this.defaultUrl = defaultUrl;
	}
}
