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

public class ApiTest extends RestTestBase{
	
	private static String EXPECTED = "Red Hat Storage Console";
	
	@Tcms("222544")
	@Test
	public void apiContainsRedhatConsoleBrandNameTest() throws ProcessResponseBodyException, RequestException{
		    
		Assert.assertEquals(200, getSession().sendTransaction(new HttpGet("/api")).getCode());
		Assert.assertTrue( getSession().sendTransaction(new HttpGet("/api")).getBody().contains(EXPECTED));
	}
	

}
