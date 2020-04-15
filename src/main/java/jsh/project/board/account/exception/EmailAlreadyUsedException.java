package jsh.project.board.account.exception;

import jsh.project.board.global.error.exception.BusinessException;
import jsh.project.board.global.error.exception.ErrorCode;

public class EmailAlreadyUsedException extends BusinessException{
	
	public EmailAlreadyUsedException() {
		super(ErrorCode.EMAIL_AREADY_USED);
	}

}
