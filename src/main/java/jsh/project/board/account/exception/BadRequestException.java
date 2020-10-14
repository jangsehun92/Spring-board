package jsh.project.board.account.exception;

import jsh.project.board.global.error.exception.BusinessException;
import jsh.project.board.global.error.exception.ErrorCode;

public class BadRequestException extends BusinessException{

	public BadRequestException() {
		super(ErrorCode.BAD_REQUEST);
	}

}
