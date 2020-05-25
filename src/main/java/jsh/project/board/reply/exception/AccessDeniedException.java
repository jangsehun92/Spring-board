package jsh.project.board.reply.exception;

import jsh.project.board.global.error.exception.BusinessException;
import jsh.project.board.global.error.exception.ErrorCode;

public class AccessDeniedException extends BusinessException{

	public AccessDeniedException() {
		super(ErrorCode.HANDLE_ACCESS_DENIED);
	}

}
