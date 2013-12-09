package com.redhat.qe.repository.rest;

import java.rmi.UnexpectedException;

import org.junit.Assert;
import org.bouncycastle.ocsp.RespData;
import org.calgb.test.performance.SimplifiedResponse;

import com.redhat.qe.exceptions.UnexpectedReponseException;
import com.redhat.qe.exceptions.UnexpectedReponseWrapperException;
import com.redhat.qe.ssh.Response;
import com.redhat.qe.ssh.IResponse;

import dstywho.regexp.RegexMatch;

public class ResponseWrapper extends Response implements IResponse{
	
	private SimplifiedResponse response;
	
	private static String stringFormat(SimplifiedResponse response){
		return String.format("%s %s\\n%s", response.getCode(), response.getMessage(), response.getBody());
	}

	public ResponseWrapper(SimplifiedResponse response ){
		super(stringFormat(response));
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

	//TODO fix magic nums
	public void expectSimilarCode(int code){
		if(!isCodeSimilar(code )){
			throw new UnexpectedReponseWrapperException(String.format("unexpected http code returned expected %sxx but recieved %s; body: %s", code/100, response.getCode(), response.getBody()), this);
		}
	}

	public boolean isCodeSimilar(int code ) {
		assert code > 100;
		assert code <= 999;
		int classCode = (int) (Math.floor(code / 100.0) * 100);
		return (response.getCode() >= classCode && response.getCode() < code + 100 );
	}



	
	


}
