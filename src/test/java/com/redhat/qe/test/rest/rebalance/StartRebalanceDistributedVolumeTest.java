package com.redhat.qe.test.rest.rebalance;

import junit.framework.Assert;

import org.junit.Test;

import com.redhat.qe.annoations.Tcms;
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

public class StartRebalanceDistributedVolumeTest extends RebalanceTestBase{

	@Test
	@Tcms({"311347","311410"})
	public void test(){
		Action action = getVolumeRepository(getHost1().getCluster()).rebalance(volume);
		ensureRebalanceHasStarted(action);
	}

	@Override
	protected Volume getVolumeToBeCreated() {
		return VolumeFactory.distributed("startrebalancetest", getHost1(), getHost2());
	}
}
