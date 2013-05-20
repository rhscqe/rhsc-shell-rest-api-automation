package com.redhat.qe.repository;

public class GlusterOption {
	private String name;

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

}
