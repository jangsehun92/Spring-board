package jsh.project.board.account.exception;

import jsh.project.board.global.error.exception.BusinessException;
import jsh.project.board.global.error.exception.ErrorCode;

public class AccountNotEmailChecked extends BusinessException{

	public AccountNotEmailChecked() {
		super(ErrorCode.ACCOUNT_DISABLED);
	}

}
