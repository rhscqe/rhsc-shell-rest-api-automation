package com.redhat.qe.exceptions;

import com.redhat.qe.repository.rest.ResponseWrapper;

public class UnexpectedReponseWrapperException extends RuntimeException {

	private ResponseWrapper response;


	public UnexpectedReponseWrapperException(String message, ResponseWrapper response) {
		super(message);
		this.response = response;
			
	}
	
	public ResponseWrapper getResponse(){
		return response;
	}
	
}
