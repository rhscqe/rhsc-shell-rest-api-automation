package com.redhat.qe.exceptions;

import com.redhat.qe.ssh.IResponse;

public class UnexpectedReponseException extends RuntimeException {

	private IResponse response;

	public UnexpectedReponseException(String message) {
		super(message);
	}

	public UnexpectedReponseException(String message, IResponse response) {
		super(message);
		this.response = response;
			
	}
	
	public IResponse getResponse(){
		return response;
	}
	
}
