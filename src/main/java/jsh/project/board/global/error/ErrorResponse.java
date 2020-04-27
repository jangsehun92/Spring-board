package jsh.project.board.global.error;

import jsh.project.board.global.error.exception.ErrorCode;

public class ErrorResponse{
	private String message;
	private int status;
	private String code;
	
	public ErrorResponse() {
		
	}
	
	ErrorResponse(ErrorCode errorCode) {
		this.message = errorCode.getMessage();
		this.status = errorCode.getStatus();
		this.code = errorCode.getCode();
	}
	
	public static ErrorResponse to(final ErrorCode code) {
        return new ErrorResponse(code);
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

	
//	public static class FieldError{
//		private String field;
//		private String value;
//		private String reason;
//		
//		private FieldError(String field, String value, String reason) {
//			this.field = field;
//			this.value = value;
//			this.reason = reason;
//		}
//		
//		public static List<FieldError> result(final String field, final String value, final String reason) {
//            List<FieldError> fieldErrors = new ArrayList<>();
//            fieldErrors.add(new FieldError(field, value, reason));
//            return fieldErrors;
//        }
//		
//		public static List<FieldError> result(BindingResult bindingResult) {
//			List<org.springframework.validation.FieldError> filedErrors = bindingResult.getFieldErrors();
//			return filedErrors.stream().map(error -> new FieldError(
//					error.getField(),
//					error.getRejectedValue() == null ? "" : error.getRejectedValue().toString(),
//					error.getDefaultMessage())).collect(Collectors.toList());
//		}
//		
//		
//		public String getField() {
//			return field;
//		}
//		public void setField(String field) {
//			this.field = field;
//		}
//		public String getValue() {
//			return value;
//		}
//		public void setValue(String value) {
//			this.value = value;
//		}
//		public String getReason() {
//			return reason;
//		}
//		public void setReason(String reason) {
//			this.reason = reason;
//		}
//		
//	}

}
