package com.redhat.qe.traceability;

import java.io.Console;
import java.util.ArrayList;
import java.util.HashSet;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.calgb.test.performance.HttpSession;
import org.calgb.test.performance.HttpSession.HttpProtocol;
import org.calgb.test.performance.ProcessResponseBodyException;
import org.calgb.test.performance.RequestException;
import org.calgb.test.performance.SimplifiedResponse;
import org.calgb.test.performance.UseSslException;

public class Tcms {
	public static void main(String[] args) throws RequestException, ProcessResponseBodyException, UseSslException{
		Console console = System.console();
		HttpSession session = new HttpSession("tcms.engineering.redhat.com", 443, HttpProtocol.HTTPS);
		char[] username = console.readPassword("tcms username:");
		char[] password = console.readPassword("tcms password:");
		session.useBasicAuthentication(new String(username), new String(password));
				ArrayList<String> parenttestcases = getTestCasesFromResponse(session.executeGet("/plans/?parent__pk=7332&t=ajax")); 
				ArrayList<String> testcases = getTestCasesFromResponse(session.executeGet("/plans/?pk=7332&t=ajax")); 
				
				HashSet<String> result = new HashSet<String>();
				result.addAll(parenttestcases);
				result.addAll(testcases);
				System.out.println(result);
				
				
	}

	/**
	 * @param response
	 * @return
	 */
	private static ArrayList<String> getTestCasesFromResponse(SimplifiedResponse response) {
		ArrayList<String> testcases = new ArrayList<String>();				
		JSONArray testplans = JSONArray.fromObject(response.getBody());
		for(Object testplan: testplans){
			JSONObject tp = JSONObject.fromObject(testplan);
			JSONObject fields = tp.getJSONObject("fields");
			JSONArray _testcases = fields.getJSONArray("case");
			for(Object tc: fields.getJSONArray("case")){
				testcases.add(tc.toString());
				
			}
		}
		return testcases;
	}

}
