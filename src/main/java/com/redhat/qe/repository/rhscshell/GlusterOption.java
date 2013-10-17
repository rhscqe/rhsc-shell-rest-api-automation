package com.redhat.qe.repository.rhscshell;

import org.apache.commons.lang.builder.HashCodeBuilder;

import com.redhat.qe.model.Volume;

public class GlusterOption {
	private String name;

	public GlusterOption(){}
	/**
	 * @param name
	 */
	public GlusterOption(String name) {
		super();
		this.name = name;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	public String toString(){
		return String.format("--option-name %s", getName());
		
	}
	
	@Override
	public boolean equals(Object o){
		return (o instanceof GlusterOption) 
				&& (getName() == null || ((GlusterOption)o).getName() == null ||((GlusterOption)o).getName().equals(getName()));
	}	
	
	@Override
	public int hashCode(){
		return new HashCodeBuilder(17,19).append(getName()).toHashCode();
	}	
	
	
	
}
