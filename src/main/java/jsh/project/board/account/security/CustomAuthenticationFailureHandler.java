package jsh.project.board.account.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import jsh.project.board.global.error.exception.ErrorCode;

public class CustomAuthenticationFailureHandler implements AuthenticationFailureHandler{

	@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
		
		String loginUrl = "/login";
		String sendEmailUrl = "/account/sendEmail";
		String url = null;
		
		if(exception instanceof InternalAuthenticationServiceException) {
			url = loginUrl;
		}
		
		if(exception instanceof BadCredentialsException) {
			url = loginUrl;
		}
		
		if(exception instanceof AuthenticationCredentialsNotFoundException) {
			url = sendEmailUrl;
		}
		
		request.setAttribute("email", request.getParameter("email"));
		request.setAttribute("errorMessage",exception.getMessage());
		request.getRequestDispatcher(url).forward(request, response);
	}

}
