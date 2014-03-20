package com.redhat.qe.helpers.rebalance;

import org.apache.log4j.Logger;

import com.redhat.qe.helpers.MountedVolume;
import com.redhat.qe.helpers.utils.FileSize;
import com.redhat.qe.model.Brick;
import com.redhat.qe.model.Host;

public class VolumePopulator {
	
	private VolumePopulationStrategy strategy;
	
	public VolumePopulator(VolumePopulationStrategy strategy){
		this.strategy = strategy;
	}
	
	public void populate(){
		strategy.populate();
	}
	
	

	
	

}
