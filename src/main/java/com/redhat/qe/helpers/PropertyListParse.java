package com.redhat.qe.helpers;

import java.util.HashMap; 

import com.redhat.qe.ssh.Response;

import dstywho.regexp.RegexMatch;

public class PropertyListParse {

	public static HashMap<String, String> parsePropertyList(String list) {
		HashMap<String, String> result = new HashMap<String, String>();
		for (RegexMatch keyvalue : new RegexMatch(list.toString()).find(".*:.*")) {
			String key = keyvalue.find("[^:]*:").get(0).getText();
			String value = keyvalue.getText().replace(key, "").trim();
			result.put(key.replaceAll("\\s*:", ""), value);
		}
		return result;
	}
}
