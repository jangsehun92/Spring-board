package jsh.project.board.reply.exception;

import jsh.project.board.global.error.exception.BusinessException;
import jsh.project.board.global.error.exception.ErrorCode;

public class ReplysNotFoundException extends BusinessException{

	public ReplysNotFoundException() {
		super(ErrorCode.REPLYS_NOT_FOUND);
	}

}
