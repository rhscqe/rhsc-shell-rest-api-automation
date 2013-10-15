package com.redhat.qe.rest;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;

import com.redhat.qe.config.RhscConfiguration;
import com.redhat.qe.exceptions.UnexpectedReponseWrapperException;
import com.redhat.qe.helpers.cleanup.CleanupTool;
import com.redhat.qe.helpers.cleanup.RestCleanupTool;
import com.redhat.qe.helpers.repository.ClusterHelper;
import com.redhat.qe.model.Cluster;
import com.redhat.qe.model.Host;
import com.redhat.qe.model.WaitUtil;
import com.redhat.qe.repository.rest.VolumeRepository;

import dstywho.timeout.Timeout;

public abstract class HostClusterTestBase extends RestTestBase{

	private ArrayList<Host> hosts;

	@Before
	public void setupHosts(){
		ArrayList<Host> results = new ArrayList<Host>();
		for(Host host: getHostsToBeCreated()){
			Cluster cluster = getCluster(host);
			host.setCluster(cluster);
			host = createAndWaitForUp(host);
			results.add(host);
		}
		hosts = results;
	}

	/**
	 * @param host
	 * @return
	 */
	private Cluster getCluster(Host host) {
		Cluster cluster = new ClusterHelper()._getClusterBasedOnName(getClusterRepository(), host.getCluster());
		if(cluster == null){
			cluster = new ClusterHelper().create(host.getCluster(), getClusterRepository(), getDatacenterRepository());
		}
		return cluster;
	}
	
	public ArrayList<Host> getHosts(){
		return hosts;
	}

	/**
	 * @return
	 */
	protected abstract List<Host> getHostsToBeCreated();
	
	@After
	public void teardownHosts(){
		for(Host host: hosts){
			deactivateAndDestroy(host);
		}
		for(Host host: hosts){
			if(getClusterRepository().isExist(host.getCluster())){
				getClusterRepository().destroy(host.getCluster());
			};
		}
		
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
	


}
