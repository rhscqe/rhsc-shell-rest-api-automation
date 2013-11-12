package com.redhat.qe.test.rest.rebalance;

import junit.framework.Assert;

import org.junit.Test;

import com.redhat.qe.config.RhscConfiguration;
import com.redhat.qe.factories.VolumeFactory;
import com.redhat.qe.model.Action;
import com.redhat.qe.model.Job;
import com.redhat.qe.model.Volume;
import com.redhat.qe.model.gluster.Task;
import com.redhat.qe.model.gluster.VolumeStatusOutput;
import com.redhat.qe.repository.JobRepository;
import com.redhat.qe.repository.glustercli.VolumeXmlRepository;
import com.redhat.qe.ssh.ExecSshSession;
import com.redhat.qe.test.rest.RebalanceTestBase;

public class StartRebalanceTest extends RebalanceTestBase{

	@Test
	@Tcms("311347")
	public void test(){
		Action action = getVolumeRepository(getHost1().getCluster()).rebalance(volume);
		Job rebalanceJob = new JobRepository(getSession()).show(action.getJob());
		Assert.assertTrue(rebalanceJob.getDescription().toLowerCase().contains("rebalancing"));
		Assert.assertTrue(rebalanceJob.getStatus().getState().toLowerCase().contains("started"));

		ExecSshSession glusterHostSshSession = ExecSshSession.fromHost( RhscConfiguration.getConfiguredHostFromBrickHost(getSession(), getHost1()));
		glusterHostSshSession.start();
		try{
			VolumeStatusOutput status = new VolumeXmlRepository( glusterHostSshSession).status(volume);
			Assert.assertTrue(status.getTasks().size() > 0);
			Task job = status.getTasks().get(0); 
			Assert.assertTrue(job.getStatusStr().contains("in progress") );
		}finally{
			glusterHostSshSession.stop();
		}

	}
	@Override
	protected Volume getVolumeToBeCreated() {
		return VolumeFactory.distributed("startrebalancetest", getHost1(), getHost2());
	}
}
