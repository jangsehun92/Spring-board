package jsh.project.board.global.error.exception;

public enum ErrorCode {
	//Common
	INVALID_INPUT_VALUE(400, "C001", " 유효하지 않은 요청입니다."),
    METHOD_NOT_ALLOWED(405, "C002", " 유효하지 않은 요청입니다."),
    ENTITY_NOT_FOUND(400, "C003", " Entity Not Found"),
    INTERNAL_SERVER_ERROR(500, "C004", "서버 오류입니다."),
    INVALID_TYPE_VALUaE(400, "C005", " Invalid Type Value"),
    HANDLE_ACCESS_DENIED(403, "C006", "접근 권한이 없습니다."),

	//account
	EMAIL_AREADY_USED(400, "A001", " 이미 사용중인 이메일입니다."),
	BAD_AUTH_REQUEST(400,"A002", " 유효하지 않은 인증 요청 입니다."),
	ACCOUNT_LOGIN_FAILED(400, "A003", " 아이디 또는 비밀번호가 다릅니다"),
	ACCOUNT_NOT_FOUND(400,"A004", " 계정을 찾을 수 없습니다."),
	ACCOUNT_DISABLED(400,"A005"," 계정이 활성화 되지 않았습니다. 이메일 인증을 완료해 주세요."),
	ACCOUNT_LOCKED(400, "A006", " 계정이 잠겼습니다."),
	PASSWORD_NOT_MATCH(400, "A007", " 기존 비밀번호가 맞지 않습니다."),
	ACCOUNT_BAD_REQUEST(400, "A008", " 계정 정보가 올바르지 않습니다."),
	PASSWORD_CHECK_FAILED(400, "A009", " 비밀번호가 서로 다릅니다."),
	
	//article
	ARTICLE_NOT_FOUND(400, "B002", "해당 게시글을 찾을 수 없습니다.");
	
	
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
