package com.redhat.qe.factories;

import com.redhat.qe.model.Datacenter;

public class DatacenterFactory {
	
	public Datacenter createDefault(){
		Datacenter dc = new Datacenter();
		dc.setName("Default");
		return dc;
	}

}
