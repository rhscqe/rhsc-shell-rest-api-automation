package com.redhat.qe.exceptions;

public class UnexpectedReponseException extends RuntimeException {

	/**
	 * 
	 */
	public UnexpectedReponseException() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param message
	 * @param cause
	 * @param enableSuppression
	 * @param writableStackTrace
	 */
	public UnexpectedReponseException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param message
	 * @param cause
	 */
	public UnexpectedReponseException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param message
	 */
	public UnexpectedReponseException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param cause
	 */
	public UnexpectedReponseException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}

}
