package com.redhat.qe.model;

public class Brick {
	private Host host;
	private String dir;
	
	public String toString(){
		return String.format("brick.server_id=%s,brick.brick_dir=%s", host.getId(), dir);
	}

	/**
	 * @return the host
	 */
	public Host getHost() {
		return host;
	}

	/**
	 * @param host the host to set
	 */
	public void setHost(Host host) {
		this.host = host;
	}

	/**
	 * @return the dir
	 */
	public String getDir() {
		return dir;
	}

	/**
	 * @param dir the dir to set
	 */
	public void setDir(String dir) {
		this.dir = dir;
	}
	
	
}
