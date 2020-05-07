package jsh.project.board.account.exception;

import jsh.project.board.global.error.exception.BusinessException;
import jsh.project.board.global.error.exception.ErrorCode;

public class PasswordNotMatchException extends BusinessException{

	public PasswordNotMatchException() {
		super(ErrorCode.PASSWORD_NOT_MATCH);
	}

}
