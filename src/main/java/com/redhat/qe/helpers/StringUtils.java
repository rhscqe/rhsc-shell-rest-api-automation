package com.redhat.qe.helpers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Set;

import com.google.common.base.Function;
import com.google.common.base.Joiner;
import com.google.common.base.Predicates;
import com.google.common.collect.Collections2;

import dstywho.regexp.RegexMatch;

public class StringUtils extends org.apache.commons.lang.StringUtils {
	public static HashMap<String, String> keyAttributeToHash(String keyValue) {
		HashMap<String, String> result = new HashMap<String, String>();
		Collection<String> pairs = Collections2.filter(Arrays.asList(keyValue.split("\\n")), Predicates.containsPattern(":"));
		for (String pair : pairs) {
			RegexMatch key = new RegexMatch(pair).find("[^:]*:").get(0);
			String value = pair.replaceFirst(key.getText(), "");
			result.put(key.getText().replaceAll("\\s*:", "").trim(), value.trim());
		}
		return result;
	}
	
	public static class RepeatingHashMap<T,V>{
		HashMap<T,ArrayList<V>> hash = new HashMap<T,ArrayList<V>>();
		
		public void put(T key, V value){
			if( hash.get(key) == null){
				hash.put(key, new ArrayList<V>());
			}
			hash.get(key).add(value);
		}
		
		public ArrayList<V> get(T key){
			return hash.get(key);
		}
		
		public V getFirst(T key){
			return hash.get(key).get(0);
		}
		
		public  Set<T> keys(){
			return hash.keySet();
		}
		
		public HashMap<T,String> flatten(){
			HashMap<T, String> result = new HashMap<T,String>();
			for(T key :keys()){
				String value = Joiner.on(",").join(hash.get(key));		
				result.put(key, value);
			}
			return result;
			
		}
		
		
	}
	
	public static RepeatingHashMap<String, String> repeatingKeyAttributeToHash(String keyValue) {
		RepeatingHashMap<String, String> result = new RepeatingHashMap<String, String>();
		Collection<String> pairs = Collections2.filter(Arrays.asList(keyValue.split("\\n")), Predicates.containsPattern(":")); 
		for (String pair : pairs) {
			RegexMatch key = new RegexMatch(pair).find("[^:]*:").get(0);
			String value = pair.replaceFirst(key.getText(), "");
			result.put(key.getText().replaceAll("\\s*:", "").trim(), value.trim());
		}
		return result;
	}

	public static Collection<String> getPropertyKeyValueSets(String raw) {
		String[] potentialClusterDefinitions = raw.split("\n\r");
		return Collections2.filter(Arrays.asList(potentialClusterDefinitions), Predicates.containsPattern(":"));
	}

	public static Collection<HashMap<String, String>> getProperties(String raw){
		Collection<String> sets = getPropertyKeyValueSets(raw);
		return Collections2.transform(sets, new Function<String, HashMap<String,String>>(){

			public HashMap<String, String> apply(String arg0) {
				return keyAttributeToHash(arg0);
			}
		});
		
	}
}
