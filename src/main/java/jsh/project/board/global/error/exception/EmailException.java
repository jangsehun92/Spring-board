package jsh.project.board.global.error.exception;

public class EmailException extends RuntimeException{
	
	private ErrorCode errorCode;
	
	public EmailException(String message, ErrorCode errorCode) {
		super(message);
		this.errorCode = errorCode;
	}
	
	public EmailException(ErrorCode errorCode) {
		super(errorCode.getMessage());
		this.errorCode = errorCode;
	}
	
	public ErrorCode getErrorCode() {
		return errorCode;
		
	}
}