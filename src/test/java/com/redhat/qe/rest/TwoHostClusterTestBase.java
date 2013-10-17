package com.redhat.qe.rest;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;

import com.redhat.qe.config.RhscConfiguration;
import com.redhat.qe.model.Cluster;
import com.redhat.qe.model.Host;

public abstract class TwoHostClusterTestBase extends HostClusterTestBase{

	private static final int HOST2_INDEX = 1;
	private static final int HOST1_INDEX = 0;
	Host host1;
	Host host2;

	@Before
	public void setupTwoHosts(){
		host1 = getHosts().get(HOST1_INDEX);
		host2 = getHosts().get(HOST2_INDEX);
	}

	protected Host getHost1ToBeCreated() {
		return RhscConfiguration.getConfiguration().getHosts().get(0);
	}

	protected Host getHost2ToBeCreated() {
		return RhscConfiguration.getConfiguration().getHosts().get(1);
	}
		
	@Override
	protected List<Host> getHostsToBeCreated() {
		ArrayList<Host> result = new ArrayList<Host>();
		result.add(getHost1ToBeCreated());
		result.add(getHost2ToBeCreated());
		return result;
	}

	/**
	 * @return the host1
	 */
	public Host getHost1() {
		return host1;
	}

	/**
	 * @return the host2
	 */
	public Host getHost2() {
		return host2;
	}


}
