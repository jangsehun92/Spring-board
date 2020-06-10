package jsh.project.board.global.error;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.BindException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import jsh.project.board.global.error.exception.BusinessException;
import jsh.project.board.global.error.exception.EmailException;
import jsh.project.board.global.error.exception.ErrorCode;

@ControllerAdvice
public class GlobalExceptionHandler {
	private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

	/**
	 * javax.validation.Valid or @Validated 으로 binding error 발생시 발생한다.
	 * HttpMessageConverter 에서 등록한 HttpMessageConverter binding 못할경우 발생
	 * 주로 @RequestBody, @RequestPart 어노테이션에서 발생
	 */
	@ExceptionHandler(MethodArgumentNotValidException.class)
	protected ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
		log.error("handleMethodArgumentNotValidException", e);
		final ErrorResponse response = new ErrorResponse(ErrorCode.ENTITY_NOT_FOUND, e.getBindingResult());
		return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
	}

	/**
	 * @ModelAttribut 으로 binding error 발생시 BindException 발생한다. 
	 * ref https://docs.spring.io/spring/docs/current/spring-framework-reference/web.html#mvc-ann-modelattrib-method-args
	 */
	@ExceptionHandler(BindException.class)
	protected ResponseEntity<ErrorResponse> handleBindException(BindException e) {
		log.error("handleBindException", e);
		final ErrorResponse response = new ErrorResponse(ErrorCode.INVALID_INPUT_VALUE, e.getBindingResult());
		return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
	}

	/**
	 * 지원하지 않은 HTTP method 호출 할 경우 발생
	 */
	@ExceptionHandler(HttpRequestMethodNotSupportedException.class)
	protected ResponseEntity<ErrorResponse> handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e) {
		log.error("handleHttpRequestMethodNotSupportedException", e);
		final ErrorResponse response = new ErrorResponse(ErrorCode.METHOD_NOT_ALLOWED);
		return new ResponseEntity<>(response, HttpStatus.METHOD_NOT_ALLOWED);
	}

	/**
	 * Authentication 객체가 필요한 권한을 보유하지 않은 경우 발생
	 * 컨트롤러 단에서 권한 인증에 실패 할 경우 메인페이지로 리다이렉트 시킨다.
	 * ReponseEntity를 이용하여 ErrorResponse를 리턴해 줄 수 있다.
	 */
	@ExceptionHandler(AccessDeniedException.class)
	protected Object handleAccessDeniedException(HttpServletRequest request, AccessDeniedException e) {
		log.error("handleAccessDeniedException", e);
		if(isAjax(request)) {
			final ErrorResponse response = new ErrorResponse(ErrorCode.HANDLE_ACCESS_DENIED);
			return new ResponseEntity<>(response, HttpStatus.valueOf(ErrorCode.HANDLE_ACCESS_DENIED.getStatus()));
		}
		return "redirect:/";
	}
	
	/**
	 * 서비스단에서 발생 할 수 있는 에러들을 처리한다.
	 */
	@ExceptionHandler(BusinessException.class)
	protected Object handleBusinessException(HttpServletRequest request, final BusinessException e) {
		log.error("handleEntityNotFoundException", e);
		final ErrorCode errorCode = e.getErrorCode();
		final ErrorResponse response = new ErrorResponse(errorCode);
		log.error(response.getCode() + " : " + response.getMessage());
		if(isAjax(request)) {
			return new ResponseEntity<>(response, HttpStatus.valueOf(errorCode.getStatus()));
		}
		return "redirect:/";
		
	}
	
	/**
	 * 이메일 인증에서 발생 할 수 있는 에러를 처리한다.
	 */
	@ExceptionHandler(EmailException.class)
	protected Object handleBusinessException(HttpServletRequest request, final EmailException e) {
		log.error("handleEntityNotFoundException", e);
		final ErrorCode errorCode = e.getErrorCode();
		final ErrorResponse response = new ErrorResponse(errorCode);
		log.error(response.getCode() + " : " + response.getMessage());
		if(isAjax(request)) {
			return new ResponseEntity<>(response, HttpStatus.valueOf(errorCode.getStatus()));
		}
		return "redirect:/auth/denied";
	}
	
	/**
	 * 나머지 exception에 관한 처리
	 */
	@ExceptionHandler(Exception.class)
	protected Object handleException(HttpServletRequest request, Exception e) {
		log.error("handleEntityNotFoundException", e);
		final ErrorResponse response = new ErrorResponse(ErrorCode.INTERNAL_SERVER_ERROR);
		log.error(response.getCode() + " : " + response.getMessage());
		if(isAjax(request)) {
			return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return "redirect:/";
	}
	
	private boolean isAjax(HttpServletRequest request) {
		if("XMLHttpRequest".equals(request.getHeader("x-requested-with"))) {
			return true;
		}
		return false;
	}

}
