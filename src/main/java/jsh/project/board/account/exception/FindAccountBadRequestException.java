package jsh.project.board.account.exception;

import jsh.project.board.global.error.exception.BusinessException;
import jsh.project.board.global.error.exception.ErrorCode;

public class FindAccountBadRequestException extends BusinessException{

	public FindAccountBadRequestException() {
		super(ErrorCode.ACCOUNT_BAD_REQUEST);
	}
	

}
