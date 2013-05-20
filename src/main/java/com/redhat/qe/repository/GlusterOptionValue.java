package com.redhat.qe.repository;

public class GlusterOptionValue {
	private GlusterOption option;
	private String value;
	
	
	
	public GlusterOptionValue(GlusterOption option, String value) {
		this.option = option;
		this.value = value;
	}
	/**
	 * 
	 */
	public GlusterOptionValue() {
		super();
		// TODO Auto-generated constructor stub
	}
	/**
	 * @return the option
	 */
	public GlusterOption getOption() {
		return option;
	}
	/**
	 * @param option the option to set
	 */
	public void setOption(GlusterOption option) {
		this.option = option;
	}
	/**
	 * @return the value
	 */
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
		return String.format("--option-name \"%s\" --option-value \"%s\"", getOption().getName(), getValue() );
	}
	
	
	

}
