package jsh.project.board.account.exception;

import jsh.project.board.global.error.exception.BusinessException;
import jsh.project.board.global.error.exception.ErrorCode;

public class PasswordCheckFailedException extends BusinessException{

	public PasswordCheckFailedException() {
		super(ErrorCode.PASSWORD_CHECK_FAILED);
	}

}
