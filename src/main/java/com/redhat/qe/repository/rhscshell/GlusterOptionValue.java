package com.redhat.qe.repository.rhscshell;

import java.util.ArrayList;
import java.util.HashMap;

import com.redhat.qe.model.Volume;

public class GlusterOptionValue {
	private String value;
	
	
	public GlusterOptionValue(){}
	public GlusterOptionValue(String string) {
		this.value = string;
	}
	public String getValue() {
		return value;
	}
	/**
	 * @param value the value to set
	 */
	public void setValue(String value) {
		this.value = value;
		
	}
	
	public String toString(){
		return String.format("--option-value \"%s\"",  getValue() );
	}
	
	@Override
	public boolean equals(Object o){
		return (o instanceof GlusterOptionValue) 
				&& (getValue() == null || ((GlusterOptionValue)o).getValue().equals(getValue()));
	}	
	
	public static HashMap<GlusterOption, GlusterOptionValue> fromHashMap(HashMap<String, String> volumeOptions) {
		HashMap<GlusterOption, GlusterOptionValue> optionValues = new HashMap<GlusterOption, GlusterOptionValue>();
		for(String key: volumeOptions.keySet()){
			optionValues.put(new GlusterOption(key), new GlusterOptionValue(volumeOptions.get(key)));
		}
		return optionValues;
	}
	
	
	

}
