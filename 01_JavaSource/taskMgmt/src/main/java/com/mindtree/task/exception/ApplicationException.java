package com.mindtree.task.exception;

public class ApplicationException extends RuntimeException{

	
	private static final long serialVersionUID = 1L;
	
	private int errorCode;
	private String errorMessage;
	
	public ApplicationException(String errorMessage) {
		this.errorMessage = errorMessage;
	}
	
	public ApplicationException(int errorCode, Exception rootCause) {
		super(rootCause);
		this.errorCode = errorCode;
	}
	
	public ApplicationException(String errorMessage, Exception newRootCause) {
		super(errorMessage, newRootCause);
		this.errorMessage = errorMessage;
	}
	
	public int getErrorCode() {
		return errorCode;
	}
	
	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public void setErrorCode(int errorCode) {
		this.errorCode = errorCode;
	}
	
	

	
	
}
