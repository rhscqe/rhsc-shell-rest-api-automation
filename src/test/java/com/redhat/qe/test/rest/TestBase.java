package com.redhat.qe.test.rest;

import org.calgb.test.performance.HttpSession;
import org.calgb.test.performance.UseSslException;
import org.junit.After;
import org.junit.Before;

import com.redhat.qe.config.RhscConfiguration;
import com.redhat.qe.helpers.HttpSessionFactory;
import com.redhat.qe.model.Cluster;
import com.redhat.qe.repository.IClusterRepository;
import com.redhat.qe.repository.IHostRepository;
import com.redhat.qe.repository.rest.ClusterRepository;
import com.redhat.qe.repository.rest.DatacenterRepository;
import com.redhat.qe.repository.rest.HostRepository;
import com.redhat.qe.repository.rest.VolumeRepository;

public abstract class TestBase implements ITestBase {
	private HttpSession session;

	@Before
	public void setup() throws UseSslException{ 
		session = new HttpSessionFactory().createHttpSession(RhscConfiguration.getConfiguration().getRestApi());
	}

	@After
	public void teardown(){
		session.stop();
	}
	
	protected HttpSession getSession(){
		return session;
	}

	/* (non-Javadoc)
	 * @see com.redhat.qe.rest.ITestBase#getClusterRepository()
	 */
	public abstract IClusterRepository getClusterRepository() ;
	

	/**
	 * @return
	 */
	protected abstract DatacenterRepository getDatacenterRepository() ;
	

	/* (non-Javadoc)
	 * @see com.redhat.qe.rest.ITestBase#getHostRepository()
	 */
	public abstract IHostRepository getHostRepository() ;

	/* (non-Javadoc)
	 * @see com.redhat.qe.rest.ITestBase#getVolumeRepository(com.redhat.qe.model.Cluster)
	 */
	public abstract VolumeRepository getVolumeRepository(Cluster cluster) ;

}
