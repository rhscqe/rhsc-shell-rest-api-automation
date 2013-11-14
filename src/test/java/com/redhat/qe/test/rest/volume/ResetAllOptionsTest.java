package com.redhat.qe.test.rest.volume;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import com.redhat.qe.config.RhscConfiguration;
import com.redhat.qe.factories.VolumeFactory;
import com.redhat.qe.model.Host;
import com.redhat.qe.model.Volume;

public class ResetAllOptionsTest extends VolumesTestBase {


	@Override
	protected List<Volume> getVolumesToBeCreated() {
		ArrayList<Volume> volumes = new ArrayList<Volume>();
		volumes.add(VolumeFactory.distributed("resetallmeoptions", getHosts().toArray(new Host[0])));
		return volumes;
	}

	@Override
	protected List<Host> getHostsToBeCreated() {
		return RhscConfiguration.getConfiguration().getHosts();
	}
	
	@Test
	public void test(){
		Volume volume = getVolumes().get(0);
		getVolumeRepository(getHosts().get(0).getCluster()).resetAllOptions(volume);
	}

}
