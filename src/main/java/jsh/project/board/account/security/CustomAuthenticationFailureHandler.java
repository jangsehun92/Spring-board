package jsh.project.board.account.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import jsh.project.board.account.service.AccountService;
import jsh.project.board.global.error.ErrorResponse;
import jsh.project.board.global.error.exception.ErrorCode;

public class CustomAuthenticationFailureHandler implements AuthenticationFailureHandler{
	private static final Logger log = LoggerFactory.getLogger(CustomAuthenticationFailureHandler.class);
	
	@Autowired
	private AccountService accountService;

	@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
		String loginUrl = "/login";
		String errorUrl = "/account/status";
		
		if(exception instanceof InternalAuthenticationServiceException) {
			log.info("InternalAuthenticationServiceException");
			dispatcherForward(request,response, loginUrl, ErrorCode.ACCOUNT_NOT_FOUND);
		}
		
		if(exception instanceof BadCredentialsException) {
			log.info("BadCredentialsException");
			String email = request.getParameter("email");
			//해당 계정의 로그인실패 횟수를 가져온다.
			int failureCount = accountService.getAccountFailureCount(email);
			failureCount += 1;
			//로그인실패 횟수가 3이면 계정을 잠구고, 계정이 잠겼다는 페이지로 이동한다.
			if(failureCount==3) {
				accountLocked(email);
				dispatcherForward(request,response, errorUrl, ErrorCode.ACCOUNT_LOCKED);
			}else {
				accountService.updateFailureCount(email, failureCount);
				dispatcherForward(request,response, loginUrl, ErrorCode.ACCOUNT_LOGIN_FAILED);
			}
		}
		
		if(exception instanceof DisabledException) {
			log.info("DisabledException");
			dispatcherForward(request,response, errorUrl, ErrorCode.ACCOUNT_DISABLED);
		}
		
		if(exception instanceof LockedException) {
			log.info("LockedException");
			dispatcherForward(request,response, errorUrl, ErrorCode.ACCOUNT_LOCKED);
		}
	}
	
	private void dispatcherForward(HttpServletRequest request, HttpServletResponse response, String url, ErrorCode errorCode) throws IOException, ServletException  {
		HttpSession session = request.getSession();
		session.setAttribute("email", request.getParameter("email"));
		final ErrorResponse errorResponse = new ErrorResponse(errorCode);
		request.setAttribute("errorResponse", errorResponse);
		request.getRequestDispatcher(url).forward(request, response);
	}
	
	private void accountLocked(String email) {
		accountService.updateLocked(email, 0);
	}
}
