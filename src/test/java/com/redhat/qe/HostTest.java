package com.redhat.qe;

import org.junit.Assert;
import org.junit.Test;

import com.redhat.qe.config.Configuration;
import com.redhat.qe.model.Cluster;
import com.redhat.qe.model.ClusterFactory;
import com.redhat.qe.model.Host;
import com.redhat.qe.model.HostFactory;
import com.redhat.qe.model.WaitUtil;
import com.redhat.qe.repository.ClusterRepository;
import com.redhat.qe.repository.HostRepository;

public class HostTest extends TestBase{
	@Test
	public void test(){
		
		Host host = Configuration.getConfiguration().getHosts().iterator().next();
		Cluster.fromResponse(new ClusterRepository(getShell()).createOrShow(host.getCluster()));
		HostRepository hostRepository = new HostRepository(getShell());
		host = hostRepository.createOrShow(host);
		Assert.assertTrue(WaitUtil.waitForHostStatus(hostRepository, host,"up", 400));
		
		hostRepository.deactivate(host);
		Assert.assertTrue(WaitUtil.waitForHostStatus(hostRepository, host,"maintenance", 400));
		hostRepository.destroy(host);
	}

}
