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
	EMAIL_NOT_CHECKED(400, "A002", " 이메일 인증이 완료 되지 않았습니다.");
	//아이디,비밀번호 관련 추가
	
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
