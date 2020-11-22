package jsh.project.board.global.error.exception;

public class AuthException extends RuntimeException{
	
	private ErrorCode errorCode;
	
	public AuthException(String message, ErrorCode errorCode) {
		super(message);
		this.errorCode = errorCode;
	}
	
	public AuthException(ErrorCode errorCode) {
		super(errorCode.getMessage());
		this.errorCode = errorCode;
	}
	
	public ErrorCode getErrorCode() {
		return errorCode;
		
	}
}
