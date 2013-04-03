package com.redhat.qe.repository.rest;

import java.rmi.UnexpectedException;

import org.junit.Assert;
import org.calgb.test.performance.SimplifiedResponse;

import com.redhat.qe.exceptions.UnexpectedReponseException;
import com.redhat.qe.exceptions.UnexpectedReponseWrapperException;

public class ResponseWrapper {
	
	private SimplifiedResponse response;

	public ResponseWrapper(SimplifiedResponse response ){
		this.response = response;
	}
	
	public String getMessage() {
		return response.getMessage();
	}


	public String getBody() {
		return response.getBody();
	}


	public int getCode() {
		return response.getCode();
	}
	
	public void expectCode(int code){
		if(code != response.getCode()){
			throw new UnexpectedReponseWrapperException(String.format("unexpected http code returned expected %s but recieved %s; body: %s", code, response.getCode(), response.getBody()), this);
		}
	}

	
	


}
