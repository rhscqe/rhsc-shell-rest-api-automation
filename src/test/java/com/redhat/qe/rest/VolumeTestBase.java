package com.redhat.qe.rest;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;

import com.redhat.qe.helpers.repository.ClusterHelper;
import com.redhat.qe.model.Cluster;
import com.redhat.qe.model.Volume;
import com.redhat.qe.repository.rest.VolumeRepository;

public abstract class VolumeTestBase extends HostClusterTestBase{
	

	private ArrayList<Volume> volumes;


	protected abstract Volume getVolumeToBeCreated();
	
	protected ArrayList<Volume> getVolumes(){
		return volumes;
	}
	
	@Before
	public void createVolume(){
		ArrayList<Volume> result = new ArrayList<Volume>();
		for(final Volume volume: getVolumesToBeCreated()){
			Cluster cluster = new ClusterHelper().getClusterBasedOnName(getClusterRepository(), volume.getCluster());
			VolumeRepository repo = new VolumeRepository(getSession(), cluster);
			result.add(repo.createOrShow(volume));
			this.volumes = result;
		}
			
	}

	/**
	 * @param volume
	 * @return
	 */

	
	protected abstract List<Volume> getVolumesToBeCreated();


}
