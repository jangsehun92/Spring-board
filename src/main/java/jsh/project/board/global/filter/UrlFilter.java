package jsh.project.board.global.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UrlFilter implements Filter{
	
	private static final Logger log = LoggerFactory.getLogger(UrlFilter.class);

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		log.info("UrlFilter init()");
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		
		if(!req.getServletPath().startsWith("/resources")) {
			log.info(req.getScheme() + " " + req.getMethod() + " " + req.getRequestURI());
		}
		chain.doFilter(request, response);
	}
	
	@Override
	public void destroy() {
		
	}

}
