package jsh.project.board.account.exception;

import jsh.project.board.global.error.exception.AuthException;
import jsh.project.board.global.error.exception.ErrorCode;

public class BadAuthRequestException extends AuthException{

	public BadAuthRequestException() {
		super(ErrorCode.BAD_AUTH_REQUEST);
	}

}
