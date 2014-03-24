package com.redhat.qe.helpers.rebalance;

import org.apache.log4j.Logger;

import com.redhat.qe.helpers.utils.FileSize;

public class EmptyVolumePopulationStrategy extends VolumePopulationStrategy{
	private static final Logger LOG = Logger.getLogger(EmptyVolumePopulationStrategy.class);

	@Override
	protected void populate(FileSize fileSizeToPopulateWith, FileSize maxDataToWrite) {
		LOG.info("finished populating 0 MB to volume");
	}
	
	
}