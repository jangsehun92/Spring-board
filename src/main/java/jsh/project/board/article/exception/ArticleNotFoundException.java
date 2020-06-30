package jsh.project.board.article.exception;

import jsh.project.board.global.error.exception.BusinessException;
import jsh.project.board.global.error.exception.ErrorCode;

public class ArticleNotFoundException extends BusinessException{

	public ArticleNotFoundException() {
		super(ErrorCode.ARTICLE_NOT_FOUND);
	}
	
	

}
