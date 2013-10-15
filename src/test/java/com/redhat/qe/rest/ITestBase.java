package com.redhat.qe.rest;

import com.redhat.qe.model.Cluster;
import com.redhat.qe.repository.IClusterRepository;
import com.redhat.qe.repository.IHostRepository;
import com.redhat.qe.repository.rest.VolumeRepository;

public interface ITestBase {

	/**
	 * @return
	 */
	public abstract IClusterRepository getClusterRepository();

	public abstract IHostRepository getHostRepository();

	public abstract VolumeRepository getVolumeRepository(Cluster cluster);

}