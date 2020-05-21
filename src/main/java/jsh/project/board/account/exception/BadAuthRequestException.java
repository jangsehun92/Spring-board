package jsh.project.board.account.exception;

import jsh.project.board.global.error.exception.EmailException;
import jsh.project.board.global.error.exception.ErrorCode;

public class BadAuthRequestException extends EmailException{

	public BadAuthRequestException() {
		super(ErrorCode.BAD_AUTH_REQUEST);
	}

}
