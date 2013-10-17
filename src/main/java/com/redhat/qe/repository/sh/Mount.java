package com.redhat.qe.repository.sh;

public class Mount {
	
	private String mountPoint;
	private String device;
	private String type;

	public Mount(String type,  String device,String mountPoint){
		this.type = type;
		this.device = device;
		this.mountPoint = mountPoint;
	}
	
	
	public String toString(){
		return String.format("mount -t %s %s %s", type, device, mountPoint);
	}

}
