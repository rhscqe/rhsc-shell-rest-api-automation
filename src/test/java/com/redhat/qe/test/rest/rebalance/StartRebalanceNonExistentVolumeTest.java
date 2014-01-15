package com.redhat.qe.test.rest.rebalance;

import junit.framework.Assert;

import org.junit.Test;

import com.redhat.qe.annoations.Tcms;
import com.redhat.qe.factories.VolumeFactory;
import com.redhat.qe.model.Volume;
import com.redhat.qe.repository.rest.ResponseWrapper;
import com.redhat.qe.test.rest.TwoHostClusterTestBase;

public class StartRebalanceNonExistentVolumeTest extends TwoHostClusterTestBase{

	@Test
	@Tcms("311350")
	public void test(){
		Volume volume = VolumeFactory.distributedUneven("StartRebOnInvalidVol", getHost1(), getHost2());
		volume.setId("thisisafakeid");
		ResponseWrapper resp = getVolumeRepository(getHost1().getCluster())._rebalance(volume);
		Assert.assertEquals(404, resp.getCode());
	}

}
