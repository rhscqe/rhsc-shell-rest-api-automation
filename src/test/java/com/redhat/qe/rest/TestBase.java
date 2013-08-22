package com.redhat.qe.rest;

import org.calgb.test.performance.HttpSession;
import org.calgb.test.performance.HttpSession.HttpProtocol;
import org.calgb.test.performance.UseSslException;
import org.junit.After;
import org.junit.Before;

import com.redhat.qe.config.RestApi;
import com.redhat.qe.config.RhscConfiguration;
import com.redhat.qe.helpers.HttpSessionFactory;
import com.redhat.qe.model.Datacenter;
import com.redhat.qe.repository.rest.ClusterRepository;
import com.redhat.qe.repository.rest.DatacenterRepository;
import com.redhat.qe.repository.rest.HostRepository;

public class TestBase {
	private HttpSession session;

	@Before
	public void setup() throws UseSslException{ 
		session = new HttpSessionFactory().createHttpSession(RhscConfiguration.getConfiguration().getRestApi());
	}

	@After
	public void teardown(){
		session.stop();
	}
	
	public HttpSession getSession(){
		return session;
	}

	/**
	 * @return
	 */
	ClusterRepository getClusterRepository() {
		return new ClusterRepository(getSession());
	}
	
	Datacenter defaultDatatcenter() {
		return new DatacenterRepository(getSession()).list().get(0);
	}
	

	HostRepository getHostRepository() {
		return new HostRepository(getSession());
	}


}
