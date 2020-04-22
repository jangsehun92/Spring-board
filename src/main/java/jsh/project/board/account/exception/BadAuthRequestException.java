package jsh.project.board.account.exception;

import jsh.project.board.global.error.exception.BusinessException;
import jsh.project.board.global.error.exception.ErrorCode;

public class BadAuthRequestException extends BusinessException{

	public BadAuthRequestException() {
		super(ErrorCode.BAD_AUTH_REQUEST);
	}

}
