package com.redhat.qe;

import org.junit.Assert;
import org.junit.Test;

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
		Cluster cluster = ClusterFactory.cluster("HostTest");
		cluster = Cluster.fromResponse(new ClusterRepository(getShell()).createOrShow(cluster));
		
		Host host = HostFactory.create("node1", "rhsc-qa9-node-a", "redhat", cluster);
		HostRepository hostRepository = new HostRepository(getShell());
		host = hostRepository.createOrShow(host);
		System.out.println("---------------------------");
		System.out.println("---------------------------");
		System.out.println("---------------------------");
		System.out.println(host.getName());		
		System.out.println("---------------------------");
		System.out.println("---------------------------");
		System.out.println("---------------------------");
		Assert.assertTrue(WaitUtil.waitForHostStatus(hostRepository, host,"up", 400));
		
		hostRepository.deactivate(host);
		Assert.assertTrue(WaitUtil.waitForHostStatus(hostRepository, host,"maintenance", 400));
		hostRepository.destroy(host);
		
	}

}
