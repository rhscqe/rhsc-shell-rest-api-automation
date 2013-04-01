package com.redhat.qe.model;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;


import com.google.common.base.Predicates;
import com.google.common.collect.Collections2;

public class StringUtils extends org.apache.commons.lang.StringUtils{
	public static HashMap<String, String> keyAttributeToHash(String keyValue){
		HashMap<String,String> result = new HashMap<String,String>();
		Collection<String> pairs = Collections2.filter(Arrays.asList(keyValue.split("\\n")), Predicates.containsPattern(":"));
		for(String pair: pairs){
			 String[] keyToValue = pair.split(":");
			 result.put(keyToValue[0].trim(), keyToValue[1].trim());
		}
		return result;
	}
}
