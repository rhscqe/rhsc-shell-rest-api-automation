package com.redhat.qe.test.rest;

import junit.framework.Assert;

import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Before;

import com.redhat.qe.factories.BrickFactory;
import com.redhat.qe.helpers.rebalance.BrickPopulator;
import com.redhat.qe.helpers.utils.AbsolutePath;
import com.redhat.qe.helpers.utils.FileNameHelper;
import com.redhat.qe.helpers.utils.FileSize;
import com.redhat.qe.model.Action;
import com.redhat.qe.model.Job;
import com.redhat.qe.model.Volume;
import com.redhat.qe.model.gluster.Task;
import com.redhat.qe.model.gluster.VolumeStatusOutput;
import com.redhat.qe.repository.JobRepository;
import com.redhat.qe.repository.rest.BrickRepository;
import com.redhat.qe.repository.sh.DD;
import com.redhat.qe.ssh.ExecSshSession;


public abstract class RebalanceTestBase extends PopulatedVolumeTestBase {
	private static final Logger LOG = Logger.getLogger(RebalanceTestBase.class);

	@Override
	protected void populateVolume() {
		AbsolutePath file = mountPoint.add(FileNameHelper.randomFileName());
		ExecSshSession sshSession = ExecSshSession.fromHost(mounter);
		sshSession.start();
		try {
			for(int i=0; i< 26; i ++){
				sshSession.runCommandAndAssertSuccess(DD.writeZeros(file.toString(),FileSize.megaBytes(500)).toString());
				sshSession.runCommandAndAssertSuccess(DD.writeZeros(file.toString(),FileSize.megaBytes(500)).toString());
			}
		} finally {
			sshSession.stop();
 		}
	}

	@Before
	public void addEmptyBricks(){
		getBrickRepo().createWithoutBodyExpected(new BrickFactory().brick(getHost2()));
		getBrickRepo().createWithoutBodyExpected(new BrickFactory().brick(getHost1()));
		getBrickRepo().createWithoutBodyExpected(new BrickFactory().brick(getHost2()));
		getBrickRepo().createWithoutBodyExpected(new BrickFactory().brick(getHost1()));
		LOG.info("bricks added");
		LOG.info("end of RebalanceTestBase fixture");
	}


	 protected abstract Volume getVolumeToBeCreated() ;

	/**
	 * @return
	 */
	protected BrickRepository getBrickRepo() {
		return new BrickRepository(getSession(), getHost1().getCluster(), volume);
	}
	
	@After
	public void afterrebalance(){
		getVolumeRepository()._stopRebalance(volume);
	}

	
	protected void ensureRebalanceHasStarted(Action action) {
		Job rebalanceJob = new JobRepository(getSession()).show(action.getJob());
		Assert.assertTrue(rebalanceJob.getDescription().toLowerCase().contains("rebalancing"));
		Assert.assertTrue(rebalanceJob.getStatus().getState().toLowerCase().contains("started"));

		VolumeStatusOutput status = getGlusterVolumeStatus();
		Assert.assertTrue(status.getTasks().size() > 0);
		Task job = status.getTasks().get(0); 
		Assert.assertTrue(job.getStatusStr().contains("in progress") );
	}

}
