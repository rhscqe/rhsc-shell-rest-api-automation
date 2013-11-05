package com.redhat.qe.rebalance;

import junit.framework.Assert;

import org.junit.Test;

import com.redhat.qe.annoations.Tcms;
import com.redhat.qe.model.Volume;
import com.redhat.qe.repository.rest.ResponseWrapper;
import com.redhat.qe.repository.rest.VolumeRepository;
import com.redhat.qe.rest.TwoHostClusterTestBase;

public class StopRebalanceOnNonExistentVolume extends TwoHostClusterTestBase{
	@Test
	@Tcms("318691")
	public void stopReblanceWhenNoRebalanceInProgress(){
		Volume nonexistantVolume = new Volume();
		nonexistantVolume.setId("thisiddoesnotexist");
		ResponseWrapper result = getVolumeRepository().stopRebalance(nonexistantVolume);
		int code = result.getCode();
		Assert.assertEquals(404, code);
	}

	private VolumeRepository getVolumeRepository() {
		return new VolumeRepository(getSession(), getHost1().getCluster());
	}

}
