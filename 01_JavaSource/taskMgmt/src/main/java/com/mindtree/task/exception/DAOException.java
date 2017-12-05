package com.mindtree.task.exception;


public class DAOException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	private int errorCode;

	private boolean loginError;

	
	public DAOException(String message) {
		super(message);
	}

	
	public DAOException(String message, Exception newRootCause) {
		super(message, newRootCause);
	}

	public DAOException(int errorCode) {
		this.errorCode = errorCode;
	}

	public int getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(int errorCode) {
		this.errorCode = errorCode;
	}

	public boolean isLoginError() {
		return loginError;
	}

	public void setLoginError(boolean loginError) {
		this.loginError = loginError;
	}
}
