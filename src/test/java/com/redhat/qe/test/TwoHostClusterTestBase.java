package com.redhat.qe.test;

import java.util.Iterator;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;

import com.redhat.qe.config.RhscConfiguration;
import com.redhat.qe.exceptions.UnexpectedReponseException;
import com.redhat.qe.model.Cluster;
import com.redhat.qe.model.Host;
import com.redhat.qe.model.WaitUtil;
import com.redhat.qe.repository.ClusterRepository;
import com.redhat.qe.ssh.Response;

import dstywho.timeout.Duration;

public class TwoHostClusterTestBase extends OpenShellSessionTestBase {

	protected static Host host1;
	protected static Host host2;
	private static Cluster cluster;

	@Before
	public void setup() {
		cluster = new ClusterRepository(getShell()).createOrShow(RhscConfiguration.getConfiguration().getCluster());
		Iterator<Host> hosts = RhscConfiguration.getConfiguration().getHosts().iterator();
	
		host1 = getHostRepository().createOrShow(hosts.next());
		Assert.assertTrue(String.format("host did not come up; host is in ", host1.toJson()),
				WaitUtil.waitForHostStatus(getHostRepository(), host1, "up", 400));
		host2 = getHostRepository().createOrShow(hosts.next());
		Assert.assertTrue(String.format("host did not come up; host is in ", host2.toJson()),
				WaitUtil.waitForHostStatus(getHostRepository(), host2, "up", 400));
		;
	}

	@After
	public void teardown() {
		destroyHost( host1);
		destroyHost( host2);
		getClusterRepository().destroy(cluster);
		
	}

	/**
	 * @param hostRepository
	 */
	private void destroyHost(Host host) {
		if (host != null) {
			getHostRepository()._deactivate(host);
			Assert.assertTrue(WaitUtil.waitForHostStatus(getHostRepository(), host, "maintenance", 400));
			Response response = getHostRepository()._destroy(host, null);
			if(response.contains("locked")){
				Duration.ONE_SECOND.sleep();
				getHostRepository().destroy(host);
			}else if(response.contains("complete")){
				return;
			}else{
				throw new UnexpectedReponseException("expecting 'complete'",response);
				
			}
		}
	}

	public TwoHostClusterTestBase() {
		super();
	}

}