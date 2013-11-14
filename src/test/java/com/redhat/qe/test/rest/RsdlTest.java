package com.redhat.qe.test.rest;

import org.apache.http.client.methods.HttpGet;
import org.calgb.test.performance.ProcessResponseBodyException;
import org.calgb.test.performance.RequestException;
import org.calgb.test.performance.UseSslException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.Test;
import org.testng.Assert;

import com.google.inject.spi.Element;
import com.redhat.qe.annoations.Tcms;

public class RsdlTest extends RestTestBase{
	@Test
	public void test() throws UseSslException, ProcessResponseBodyException, RequestException{
//		getSession().useBasicAuthentication("admin@internal", "redhat");
		Assert.assertEquals(200, getSession().sendTransaction(new HttpGet("/api?rsdl")).getCode());
	}
	
	private static String[] EXPECTED_LINKS= {"capabilities","clusters","events","hosts","networks","roles","tags","users","groups","domains"};
	
	@Tcms("222544")
	@Test
	public void doesnotcontainVirtualazationStuffTest() throws ProcessResponseBodyException, RequestException{
	    String rsdl = getSession().sendTransaction(new HttpGet("/api?rsdl")).getBody();
	    Document doc = Jsoup.parse(rsdl);
	    for (org.jsoup.nodes.Element link: doc.select("link")){
	    	String linkpath = link.attr("href");
			Assert.assertTrue(isAExpectedLink(linkpath), linkpath  + " was not an expected link");
	    }
	    
	}
	
	private boolean isAExpectedLink(String path){
		for(String expected : EXPECTED_LINKS){
			if(path.contains(expected))
				return true;
		}
		return false;

		
	}

}
