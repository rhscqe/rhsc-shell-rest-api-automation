package com.redhat.qe.rest;

import java.util.Iterator;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;

import com.redhat.qe.config.RhscConfiguration;
import com.redhat.qe.exceptions.UnexpectedReponseWrapperException;
import com.redhat.qe.model.Cluster;
import com.redhat.qe.model.Datacenter;
import com.redhat.qe.model.Host;
import com.redhat.qe.model.WaitUtil;
import com.redhat.qe.repository.rest.DatacenterRepository;
import com.redhat.qe.repository.rest.VolumeRepository;

import dstywho.timeout.Timeout;

public class TwoHostClusterTestBase extends TestBase {

	Cluster cluster;
	Host host1;
	Host host2;

	@Before
	public void setupCluster(){

	   this.cluster = createOrShowCluster();

		Iterator<Host> hosts = RhscConfiguration.getConfiguration().getHosts().iterator();
			host1 = hosts.next();
			host1 = createAndWaitForUp(host1);
			host2 = hosts.next();
			host2 = createAndWaitForUp(host2);
			

			
	}
	@After
	public void teardownCluster(){
		deactivateAndDestroy(host1);
		deactivateAndDestroy(host2);
		
		
	   getClusterRepository().delete(cluster);
		
	}
	/**
	 * @return 
	 * 
	 */
	private Cluster createOrShowCluster() {
		Cluster result = RhscConfiguration.getConfiguration().getCluster();
		result = getClusterRepository().createOrShow(result);
		return result;
	}
	private void deactivateAndDestroy(Host host){
		getHostRepository().deactivate(host);
			Assert.assertTrue(WaitUtil.waitForHostStatus(getHostRepository(), host,"maintenance", 400));
			destroy(host);
			
	}
	
	private void destroy(Host host){
		for(int i=0; i < 5; i ++){
			try{
				getHostRepository().destroy(host);
				return;
			}catch(UnexpectedReponseWrapperException e){
				if(!e.getResponse().getMessage().contains("locked")){
					throw e;
				}else{
					Timeout.TIMEOUT_ONE_SECOND.sleep();
				}
			}
		}
		
	}

	private Host createAndWaitForUp(Host host) {
		host = getHostRepository().createOrShow(host);
		if(host.getState().equals("maintenance"))
			getHostRepository().activate(host);
		WaitUtil.waitForHostStatus(getHostRepository(), host, "up", 30);
		return host;
	}
	
	VolumeRepository getVolumeRepository() {
		return new VolumeRepository(getSession(), cluster);
	}

}
