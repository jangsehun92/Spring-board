package jsh.project.board.reply.exception;

import jsh.project.board.global.error.exception.BusinessException;
import jsh.project.board.global.error.exception.ErrorCode;

public class ReplyNotFoundException extends BusinessException{

	public ReplyNotFoundException() {
		super(ErrorCode.REPLY_NOT_FOUND);
	}

}
