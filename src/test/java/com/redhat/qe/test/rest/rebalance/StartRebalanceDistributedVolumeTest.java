package com.redhat.qe.test.rest.rebalance;

import org.junit.Test;

import com.redhat.qe.annoations.Tcms;
import com.redhat.qe.factories.VolumeFactory;
import com.redhat.qe.model.Action;
import com.redhat.qe.model.Volume;
import com.redhat.qe.test.rest.RebalanceTestBase;

public class StartRebalanceDistributedVolumeTest extends RebalanceTestBase{

	@Test
	@Tcms({"311347","311410"})
	public void test(){
		Action action = getVolumeRepository(getHost1().getCluster()).rebalance(volume);
		try{
			ensureRebalanceHasStarted(action);
		}finally{
			getVolumeRepository()._stopRebalance(volume);
		}
	}

	@Test
	@Tcms({"311347","311410"})
	public void testCli(){
		Action action = getVolumeRepository(getHost1().getCluster()).rebalance(volume);
		try{
			ensureRebalanceStartedFromCli();
		}finally{
			getVolumeRepository()._stopRebalance(volume);
		}
	}

	@Override
	protected Volume getVolumeToBeCreated() {
		return new VolumeFactory().distributed("start_rebal_on_dist_volume_test",6,  getHost1(), getHost2());
	}
}
