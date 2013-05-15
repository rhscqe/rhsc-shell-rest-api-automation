package com.redhat.qe.test.cluster;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.redhat.qe.annoations.Tcms;
import com.redhat.qe.exceptions.UnexpectedReponseException;
import com.redhat.qe.factories.ClusterFactory;
import com.redhat.qe.helpers.ResponseMessageMatcher;
import com.redhat.qe.model.Cluster;
import com.redhat.qe.test.OpenShellSessionTestBase;

public class ClusterDuplicateNameTest extends OpenShellSessionTestBase{
	private Cluster cluster;
	
	
	@Rule
	public ExpectedException expectedEx = ExpectedException.none();
	
	@Before
	public void beforeTest(){
		this.cluster = getClusterRepository().createOrShow(ClusterFactory.cluster("cluster_seats_taken"));
	}
	@After
	public void afterTest(){
		getClusterRepository().destroy(cluster);
		
	}
	@Test
	@Tcms("212942")
	public void createClusterNegative() {
		expectedEx.expect(UnexpectedReponseException.class);
		expectedEx.expect(new ResponseMessageMatcher("name in use"));
		cluster = getClusterRepository().create(cluster);
	}
}
