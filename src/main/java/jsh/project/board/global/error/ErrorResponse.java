package jsh.project.board.global.error;

import java.util.ArrayList;
import java.util.List;

import org.springframework.validation.BindingResult;

import jsh.project.board.global.error.exception.ErrorCode;
import jsh.project.board.global.error.exception.FieldError;

public class ErrorResponse{
	private String message;
	private int status;
	private String code;
	private List<FieldError> errors;
	
	public ErrorResponse() {
		
	}
	
	public ErrorResponse(ErrorCode errorCode) {
		this.message = errorCode.getMessage();
		this.status = errorCode.getStatus();
		this.code = errorCode.getCode();
		this.errors = new ArrayList<>();
	}
	
	public ErrorResponse(ErrorCode errorCode, BindingResult bingingResult) {
		this.message = errorCode.getMessage();
		this.status = errorCode.getStatus();
		this.code = errorCode.getCode();
		this.errors = bingingResultErrors(bingingResult);
	}
	
	public List<FieldError> bingingResultErrors(BindingResult bindingResult){
		List<FieldError> errors = new ArrayList<FieldError>();
		for(int i = 0; i < bindingResult.getFieldErrors().size(); i++) {
			errors.add(new FieldError(
								bindingResult.getFieldErrors().get(i).getField(), 
								bindingResult.getFieldErrors().get(i).getRejectedValue().toString(), 
								bindingResult.getFieldErrors().get(i).getDefaultMessage()));
		}
		return errors;
	}
	

	public String getMessage() {
		return message;
	}


	public int getStatus() {
		return status;
	}


	public String getCode() {
		return code;
	}
	
	public List<FieldError> getErrors(){
		return errors;
	}
}
