package jsh.project.board.article.exception;

import jsh.project.board.global.error.exception.BusinessException;
import jsh.project.board.global.error.exception.ErrorCode;

public class ArticlesNotFoundException extends BusinessException{

	public ArticlesNotFoundException() {
		super(ErrorCode.ARTICLES_NOT_FOUND);
	}

}
