package com.redhat.qe.repository.sh;

import java.util.ArrayList;
import java.util.HashMap;

import com.google.common.base.Joiner;

public class DD {
	public HashMap<String,String> namedParams = new HashMap<String,String>();
	
	public DD(HashMap<String, String> namedParams){
		this.namedParams = namedParams;
	}
	
	public String argsToString(){
		ArrayList<String> arguments = new ArrayList<String>();
		for(String param: namedParams.keySet()){
			arguments.add(String.format("%s=%s", param, namedParams.get(param)));
		}
		return Joiner.on(" ").join(arguments);
		
	}
	public String toString(){
		return String.format("dd %s", argsToString());
	}
			

}
