package com.redhat.qe.test.rest.rebalance;

import org.junit.Test;

import com.redhat.qe.annoations.Tcms;
import com.redhat.qe.factories.BrickFactory;
import com.redhat.qe.factories.VolumeFactory;
import com.redhat.qe.model.Action;
import com.redhat.qe.model.Volume;
import com.redhat.qe.test.rest.RebalanceTestBase;

public class StartRebalanceDistributedReplicateVolumeTest extends RebalanceTestBase{

	@Test
	@Tcms("311346")
	public void test(){
		Action action = getVolumeRepository(getHost1().getCluster()).rebalance(volume);
		ensureRebalanceHasStarted(action);
	}
	@Override
	protected Volume getVolumeToBeCreated() {
		return VolumeFactory.distributedReplicate("startrebalancetest", getHost1(), getHost2());
	}
	
	@Override
	public void addEmptyBricks(){
		getBrickRepo().createWithoutBodyExpected(BrickFactory.brick(getHost2()), BrickFactory.brick(getHost1()));
	}
}
