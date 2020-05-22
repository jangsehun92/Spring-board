package jsh.project.board.article.exception;

import jsh.project.board.global.error.exception.BusinessException;
import jsh.project.board.global.error.exception.ErrorCode;

public class NonLoginException extends BusinessException{

	public NonLoginException() {
		super(ErrorCode.NOT_LOGIN);
	}
}
