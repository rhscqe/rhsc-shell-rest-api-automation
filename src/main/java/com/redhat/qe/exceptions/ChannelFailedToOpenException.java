package com.redhat.qe.exceptions;

public class ChannelFailedToOpenException extends RuntimeException{

	/**
	 * 
	 */
	public ChannelFailedToOpenException() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param message
	 * @param cause
	 * @param enableSuppression
	 * @param writableStackTrace
	 */
	public ChannelFailedToOpenException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param message
	 * @param cause
	 */
	public ChannelFailedToOpenException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param message
	 */
	public ChannelFailedToOpenException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param cause
	 */
	public ChannelFailedToOpenException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}

}
