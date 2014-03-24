package com.redhat.qe.test.rest.volume;

import java.util.ArrayList;
import java.util.List;

import com.redhat.qe.config.RhscConfiguration;
import com.redhat.qe.factories.VolumeFactory;
import com.redhat.qe.model.Host;
import com.redhat.qe.model.Volume;
import com.redhat.qe.repository.rest.VolumeRepository;

public class SingleVolumeTestBase extends VolumesTestBase {

	@Override
	protected List<Volume> getVolumesToBeCreated() {
		ArrayList<Volume> vols = new ArrayList<Volume>();
		vols.add( new VolumeFactory().distributed("singlevoluemtestbase", getHosts().toArray(new Host[0])));
		return vols;
	}

	@Override
	protected List<Host> getHostsToBeCreated() {
		return RhscConfiguration.getConfiguration().getHosts();
	}
	
	public Volume getVolume(){
		return getVolumes().get(0);
	}
	

}
