package com.redhat.qe.test.rest.rebalance;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;

import com.redhat.qe.config.RhscConfiguration;
import com.redhat.qe.factories.VolumeFactory;
import com.redhat.qe.model.Host;
import com.redhat.qe.model.Volume;
import com.redhat.qe.test.rest.volume.VolumesTestBase;

public abstract class VolumeTestBase extends VolumesTestBase {



	@Override
	protected List<Host> getHostsToBeCreated() {
		return RhscConfiguration.getConfiguration().getHosts();
	}
	
	public Volume getVolume(){
		return getVolumes().get(0);
	}

}