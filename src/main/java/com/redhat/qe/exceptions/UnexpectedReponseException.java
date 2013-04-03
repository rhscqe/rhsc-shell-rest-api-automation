package com.redhat.qe.exceptions;

import com.redhat.qe.ssh.Response;

public class UnexpectedReponseException extends RuntimeException {

	private Response response;

	public UnexpectedReponseException(String message) {
		super(message);
	}

	public UnexpectedReponseException(String message, Response response) {
		super(message);
		this.response = response;
			
	}
	
	public Response getResponse(){
		return response;
	}
	
}
