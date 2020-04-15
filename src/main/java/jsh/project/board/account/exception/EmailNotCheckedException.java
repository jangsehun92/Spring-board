package jsh.project.board.account.exception;

import jsh.project.board.global.error.exception.BusinessException;
import jsh.project.board.global.error.exception.ErrorCode;

public class EmailNotCheckedException extends BusinessException{
	
	public EmailNotCheckedException() {
		super(ErrorCode.EMAIL_NOT_CHECKED);
	}

}
