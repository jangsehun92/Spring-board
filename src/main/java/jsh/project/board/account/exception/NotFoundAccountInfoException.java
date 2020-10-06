package jsh.project.board.account.exception;

import jsh.project.board.global.error.exception.BusinessException;
import jsh.project.board.global.error.exception.ErrorCode;

public class NotFoundAccountInfoException extends BusinessException{

	public NotFoundAccountInfoException() {
		super(ErrorCode.ACCOUNT_INFO_NOT_FOUND);
	}

}
