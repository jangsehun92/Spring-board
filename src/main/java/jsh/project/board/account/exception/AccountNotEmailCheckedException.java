package jsh.project.board.account.exception;

import jsh.project.board.global.error.exception.BusinessException;
import jsh.project.board.global.error.exception.ErrorCode;

public class AccountNotEmailCheckedException extends BusinessException{

	public AccountNotEmailCheckedException() {
		super(ErrorCode.ACCOUNT_DISABLED);
	}

}
