package jsh.project.board.account.exception;

import jsh.project.board.global.error.exception.BusinessException;
import jsh.project.board.global.error.exception.ErrorCode;

public class AccountNotFoundException extends BusinessException{

	public AccountNotFoundException() {
		super(ErrorCode.ACCOUNT_NOT_FOUND);
	}

}
