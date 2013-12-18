package com.redhat.qe.test.rest.rebalance;

import org.junit.Test;

import com.redhat.qe.annoations.Tcms;
import com.redhat.qe.config.RhscConfiguration;
import com.redhat.qe.factories.BrickFactory;
import com.redhat.qe.factories.VolumeFactory;
import com.redhat.qe.helpers.rebalance.BrickPopulator;
import com.redhat.qe.helpers.utils.FileNameHelper;
import com.redhat.qe.helpers.utils.FileSize;
import com.redhat.qe.model.Action;
import com.redhat.qe.model.Volume;
import com.redhat.qe.repository.sh.DD;
import com.redhat.qe.ssh.ExecSshSession;
import com.redhat.qe.test.rest.RebalanceTestBase;

public class StartRebalanceDistributedReplicateVolumeTest extends RebalanceTestBase{

	protected void populateVolume() {
		ExecSshSession mounterSession = ExecSshSession.fromHost( mounter);
		mounterSession.start();
		try{
			for(int i=0; i < 26; i++){
				mounterSession.runCommandAndAssertSuccess(DD.writeZeros(mountPoint.add(FileNameHelper.randomFileName()).toString(), FileSize.megaBytes(500)).toString());
			}
		}finally{
			mounterSession.stop();
		}
	}

	@Test
	@Tcms("311346")
	public void test(){
		Action action = getVolumeRepository(getHost1().getCluster()).rebalance(volume);
		try{
		ensureRebalanceHasStarted(action);
		}finally{
			getVolumeRepository()._stop(volume);
		}
	}

	@Test
	@Tcms("311346")
	public void testCli(){
		Action action = getVolumeRepository(getHost1().getCluster()).rebalance(volume);
		try{
			ensureRebalanceStartedFromCli();
		}finally{
			getVolumeRepository()._stop(volume);
		}
	}
	
	
	@Override
	protected Volume getVolumeToBeCreated() {
		return VolumeFactory.distributedReplicate("startrebalancetest", getHost1(), getHost2());
	}
	
	@Override
	public void addEmptyBricks(){
		getBrickRepo().createWithoutBodyExpected(new BrickFactory().brick(getHost2()), new BrickFactory().brick(getHost1()));
	}
}
