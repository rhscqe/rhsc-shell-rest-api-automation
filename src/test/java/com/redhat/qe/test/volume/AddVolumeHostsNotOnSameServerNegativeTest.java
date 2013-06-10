package com.redhat.qe.test.volume;

import java.util.Iterator;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.redhat.qe.annoations.Tcms;
import com.redhat.qe.config.Configuration;
import com.redhat.qe.config.RhscConfiguration;
import com.redhat.qe.exceptions.UnexpectedReponseException;
import com.redhat.qe.factories.ClusterFactory;
import com.redhat.qe.factories.HostFactory;
import com.redhat.qe.factories.VolumeFactory;
import com.redhat.qe.helpers.CleanupTool;
import com.redhat.qe.helpers.HostCleanup;
import com.redhat.qe.helpers.ResponseMessageMatcher;
import com.redhat.qe.model.Cluster;
import com.redhat.qe.model.Host;
import com.redhat.qe.model.WaitUtil;
import com.redhat.qe.repository.ClusterRepository;
import com.redhat.qe.test.OpenShellSessionTestBase;
import com.redhat.qe.test.TwoHostClusterTestBase;

public class AddVolumeHostsNotOnSameServerNegativeTest extends OpenShellSessionTestBase {
	
	private Host host2;
	private Host host1;
	private Cluster cluster2;
	private Cluster cluster1;

	@Before
	public void _before(){
		cluster1 = new ClusterRepository(getShell()).createOrShow(RhscConfiguration.getConfiguration().getCluster());
		cluster2 = new ClusterRepository(getShell()).createOrShow(ClusterFactory.cluster("cluster2"));
		
		Iterator<Host> hosts = RhscConfiguration.getConfiguration().getHosts().iterator();
		
	
		host1 = getHostRepository().createOrShow(hosts.next());
		Assert.assertTrue(String.format("host did not come up; host is in ", host1.toJson()),
				WaitUtil.waitForHostStatus(getHostRepository(), host1, "up", 400));
		
		host2 = hosts.next();
		host2.setCluster(cluster2);
		host2 = getHostRepository().createOrShow(host2);
		Assert.assertTrue(String.format("host did not come up; host is in ", host2.toJson()),
				WaitUtil.waitForHostStatus(getHostRepository(), host2, "up", 400));
		;
		
	}

	@After
	public void _after(){
		new CleanupTool().cleanup(Configuration.getINSTANCE());
	}
	@Rule
	public ExpectedException expectedEx = ExpectedException.none();
	
	
	@Test
	@Tcms("261777")
	public void test(){
		expectedEx.expect(UnexpectedReponseException.class);
		expectedEx.expect(new ResponseMessageMatcher("invalid brick server id"));
		getVolumeRepository().create(VolumeFactory.distributed("notgonnawork", host1, host2));
	}
	
}
