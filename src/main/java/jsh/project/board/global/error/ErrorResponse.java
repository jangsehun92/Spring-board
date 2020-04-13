package jsh.project.board.global.error;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.validation.BindingResult;

import jsh.project.board.global.error.exception.ErrorCode;

public class ErrorResponse{
	private String message;
	private int status;
	private List<FieldError> errors;
	private String code;
	
	public ErrorResponse() {
		
	}
	
	private ErrorResponse(ErrorCode errorCode, List<FieldError> errors) {
		this.message = errorCode.getMessage();
		this.status = errorCode.getStatus();
		this.errors = errors;
		this.code = errorCode.getCode();
	}
	
	private ErrorResponse(ErrorCode errorCode) {
		this.message = errorCode.getMessage();
		this.status = errorCode.getStatus();
		this.errors = new ArrayList<>();
		this.code = errorCode.getCode();
	}
	
	public static ErrorResponse result(ErrorCode errorCode, BindingResult bindingResult) {
		return new ErrorResponse(errorCode, FieldError.result(bindingResult));
	}
	
	public static ErrorResponse result(final ErrorCode code) {
        return new ErrorResponse(code);
    }

    public static ErrorResponse result(final ErrorCode code, final List<FieldError> errors) {
        return new ErrorResponse(code, errors);
    }
	
	
	public String getMessage() {
		return message;
	}


	public int getStatus() {
		return status;
	}


	public List<FieldError> getErrors() {
		return errors;
	}


	public String getCode() {
		return code;
	}

	
	public static class FieldError{
		private String field;
		private String value;
		private String reason;
		
		private FieldError(String field, String value, String reason) {
			this.field = field;
			this.value = value;
			this.reason = reason;
		}
		
		public static List<FieldError> result(final String field, final String value, final String reason) {
            List<FieldError> fieldErrors = new ArrayList<>();
            fieldErrors.add(new FieldError(field, value, reason));
            return fieldErrors;
        }
		
		public static List<FieldError> result(BindingResult bindingResult) {
			List<org.springframework.validation.FieldError> filedErrors = bindingResult.getFieldErrors();
			return filedErrors.stream().map(error -> new FieldError(
					error.getField(),
					error.getRejectedValue() == null ? "" : error.getRejectedValue().toString(),
					error.getDefaultMessage())).collect(Collectors.toList());
		}
		
		
		public String getField() {
			return field;
		}
		public void setField(String field) {
			this.field = field;
		}
		public String getValue() {
			return value;
		}
		public void setValue(String value) {
			this.value = value;
		}
		public String getReason() {
			return reason;
		}
		public void setReason(String reason) {
			this.reason = reason;
		}
		
	}

}
