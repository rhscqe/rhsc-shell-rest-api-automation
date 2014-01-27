package com.redhat.qe.repository.glustercli;

import java.util.Map;

import com.redhat.qe.helpers.utils.StringUtils;

public class VolumeOption {
	
	public static VolumeOption parse(String raw){
		return parse(StringUtils.keyAttributeToHash(raw));
	}
	
	public static VolumeOption parse(Map<String,String> keyValue){
		VolumeOption result = new VolumeOption();
		result.setDefaultValue(keyValue.get("Default Value"));
		result.setName(keyValue.get("Option"));
		result.setDescription(keyValue.get("Description"));
		return result;
	}
	
	String name;
	String defaultValue;
	String description;

	public VolumeOption() {

	}

	public VolumeOption(String name, String defaultValue, String description) {
		super();
		this.name = name;
		this.defaultValue = defaultValue;
		this.description = description;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDefaultValue() {
		return defaultValue;
	}
	public void setDefaultValue(String defaultValue) {
		this.defaultValue = defaultValue;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	
	
}
