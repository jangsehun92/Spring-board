package jsh.project.board.account.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;

public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler{
	
	//spring security 에서 제공하는 사용자 요청을 저장하고 꺼낼 수 있는 객체
	private RequestCache requestCahe = new HttpSessionRequestCache();
	private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();
	
	//login 시도한 값
	private String accountEmail;
	//login 성공시 redirect 해줄 url
	private String defaultUrl;

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
		resultRedirectStrategy(request, response, authentication);
	}
	
	//login 성공시 Redirect할 Url 결정
	protected void resultRedirectStrategy(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
		//login View로 전환되기 전 url의 값을 가져온다.(권한이 필요한 요청을 했을 때, 로그인이 되어있지 않으면 로그인 View로 가기 때문이다)
		SavedRequest savedRequest = requestCahe.getRequest(request, response);
		
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
		if(session != null) {
			session.removeAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
		}
	}
	
	public String getAccountEmail() {
		return accountEmail;
	}
	
	public void setAccountEmail(String email) {
		this.accountEmail = email;
	}
	
	public String getDefaultUrl() {
		return defaultUrl;
	}
	
	public void setDefaultUrl(String defaultUrl) {
		this.defaultUrl = defaultUrl;
	}

}