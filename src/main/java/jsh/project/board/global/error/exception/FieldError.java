package jsh.project.board.global.error.exception;

public class FieldError {
	private String field;
	private String value;
	private String reason;

	public FieldError(String field, String value, String reason) {
		this.field = field;
		this.value = value;
		this.reason = reason;
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
