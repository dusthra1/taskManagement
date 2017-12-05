package com.mindtree.task.util;

/**
 * 
 * Class that can be used as a return parameter in several service methods. Used
 * mainly in services, when the status and a specific return object is required
 * for further processing
 * 
 */
public class Status {
	
	private String status;
	private String errorCode;
	private String errorMessage;
	private Object returnObject;
	
	public String getStatus() {
		return status;
	}
	public String getErrorCode() {
		return errorCode;
	}
	public String getErrorMessage() {
		return errorMessage;
	}
	public Object getReturnObject() {
		return returnObject;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
	public void setReturnObject(Object returnObject) {
		this.returnObject = returnObject;
	}
	
	

}
