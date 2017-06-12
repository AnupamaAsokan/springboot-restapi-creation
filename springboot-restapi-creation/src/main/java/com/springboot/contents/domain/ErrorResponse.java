package com.springboot.contents.domain;
import java.io.Serializable;

/**
 * Error response domain class
 * @author anupama
 *
 */
public class ErrorResponse implements Serializable {
	

	private static final long serialVersionUID = 1L;
	private Integer errorCode;
	private String error;
	private String errorMessage;
	
	
public String getError() {
		return error;
	}



	public void setError(String error) {
		this.error = error;
	}



/**
	 * @param errorCode
	 * @param errorMessage
	 */
	public ErrorResponse(Integer errorCode, String error, String errorMessage) {
		super();
		this.errorCode = errorCode;
		this.error = error;
		this.errorMessage = errorMessage;
	}



	public Integer getErrorCode() {
		return errorCode;
	}



	public void setErrorCode(Integer errorCode) {
		this.errorCode = errorCode;
	}



	public String getErrorMessage() {
		return errorMessage;
	}



	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}



	public static long getSerialversionuid() {
		return serialVersionUID;
	}


@Override
public String toString() {
    return String.format(
            "ErrorResponse[code=%s, error='%s', message='%s]",
            errorCode.toString(),error, errorMessage );
}

}




