package jsh.project.board.global.error.exception;

public enum ErrorCode {
	//Common
	INVALID_INPUT_VALUE(400, "C001", " Invalid Input Value"),
    METHOD_NOT_ALLOWED(405, "C002", " Invalid Input Value"),
    ENTITY_NOT_FOUND(400, "C003", " Entity Not Found"),
    INTERNAL_SERVER_ERROR(500, "C004", "Server Error"),
    INVALID_TYPE_VALUE(400, "C005", " Invalid Type Value"),
    HANDLE_ACCESS_DENIED(403, "C006", "Access is Denied"),

	//account
	EMAIL_AREADY_USED(400, "A001", " 이미 사용중인 이메일입니다."),
	EMAIL_NOT_CHECKED(400, "A002", "이메일 인증이 완료되지 않았습니다."),
	EMAIL_AREADY_CHECKED(400,"A002", "이메일 인증을 완료한 계정입니다."),
	ACCOUNT_LOGIN_FAILED(400, "A003", "아이디 또는 비밀번호가 다릅니다"),
	ACCOUNT_NOT_FOUND(400,"A004", "존재하지 않는 계정입니다."),
	ACCOUNT_DISABLED(400,"A005","잠긴 계정입니다.");
	
	//article
	
	
	private int status;
	private String code;
	private String message;
	
	ErrorCode(int status, String code, String message) {
		this.status = status;
		this.code = code;
		this.message = message;
	}
	
	public int getStatus() {
		return this.status;
	}
	
	public String getCode() {
		return this.code;
	}
	
	public String getMessage() {
		return this.message;
	}
	
	
}
