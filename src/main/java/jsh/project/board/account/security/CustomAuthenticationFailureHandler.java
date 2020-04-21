package jsh.project.board.account.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import jsh.project.board.account.service.AccountService;
import jsh.project.board.global.error.exception.ErrorCode;

public class CustomAuthenticationFailureHandler implements AuthenticationFailureHandler{
	
	@Autowired
	private AccountService accountService;

	@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
		String email = request.getParameter("email");
		String url = null;
		
		String loginUrl = "/login";
		String authPage = "/account/auth";
		
		String errorMessage = null;
		
		if(exception instanceof InternalAuthenticationServiceException) {
			url = loginUrl;
			errorMessage = ErrorCode.ACCOUNT_NOT_FOUND.getMessage();
		}
		
		if(exception instanceof BadCredentialsException) {
			//해당 계정의 로그인실패 횟수를 가져온다.
			int failureCount = accountService.accountFailureCount(email);
			failureCount += 1;
			//로그인실패 횟수가 3이면 계정을 잠구고, 계정이 잠겼다는 페이지로 이동한다.
			if(failureCount==3) {
				accountLocked(email);
				url = authPage;
				request.setAttribute("errorCode", ErrorCode.ACCOUNT_DISABLED.getCode());
				errorMessage = ErrorCode.ACCOUNT_DISABLED.getMessage();
			}else {
				accountService.updateFailureCount(email, failureCount);
				url = loginUrl;
				errorMessage = ErrorCode.ACCOUNT_LOGIN_FAILED.getMessage();
			}
		}
		
		if(exception instanceof DisabledException) {
			url = authPage;
			request.setAttribute("errorCode", ErrorCode.EMAIL_NOT_CHECKED.getCode());
			errorMessage = ErrorCode.EMAIL_NOT_CHECKED.getMessage();
		}
		
		if(exception instanceof LockedException) {
			url = authPage;
			request.setAttribute("errorCode", ErrorCode.ACCOUNT_DISABLED.getCode());
			errorMessage = ErrorCode.ACCOUNT_DISABLED.getMessage();
		}
		
		request.setAttribute("email", email);
		request.setAttribute("errorMessage",errorMessage);
		request.getRequestDispatcher(url).forward(request, response);
	}
	
	public void accountLocked(String email) {
		accountService.updateLocked(email, 1);
	}
	
}
