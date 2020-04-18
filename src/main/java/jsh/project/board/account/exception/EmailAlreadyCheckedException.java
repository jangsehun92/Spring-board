package jsh.project.board.account.exception;

import jsh.project.board.global.error.exception.BusinessException;
import jsh.project.board.global.error.exception.ErrorCode;

public class EmailAlreadyCheckedException extends BusinessException{

	public EmailAlreadyCheckedException() {
		super(ErrorCode.EMAIL_AREADY_CHECKED);
	}

}
