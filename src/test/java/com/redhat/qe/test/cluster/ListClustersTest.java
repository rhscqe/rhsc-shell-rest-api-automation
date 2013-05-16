package com.redhat.qe.test.cluster;

import static org.junit.Assert.*;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.redhat.qe.annoations.Tcms;
import com.redhat.qe.factories.ClusterFactory;
import com.redhat.qe.helpers.Asserts;
import com.redhat.qe.helpers.StringUtils;
import com.redhat.qe.model.Cluster;
import com.redhat.qe.test.OpenShellSessionTestBase;

public class ListClustersTest extends OpenShellSessionTestBase{
	private Cluster c1;
	private Cluster c2;
	private Cluster c3;
	
	@Before
	public void beforeThis(){
		c1 = getClusterRepository().createOrShow(ClusterFactory.cluster("c1"));
		c2 = getClusterRepository().createOrShow(ClusterFactory.cluster("c2"));
		c3 = getClusterRepository().createOrShow(ClusterFactory.cluster("TestCluster"));
		
	}

	@After
	public void afterThis(){
		getClusterRepository().destroy(c1);
		getClusterRepository().destroy(c2);
		getClusterRepository().destroy(c3);
	}
	@Test
	@Tcms("250545")
	public void test(){
		List<Cluster> clusters = getClusterRepository().list();
		clusters.contains(c1);
		clusters.contains(c2);
	}
	
	@Test
	@Tcms("250548")
	public void testMax(){
		List<Cluster> clusters = getClusterRepository().list("--max 2");
		assertEquals("num clusters",clusters.size() ,2);
	}
	
	@Test
	@Tcms("250547")
	public void testShowAll(){
		Collection<HashMap<String, String>> properties = StringUtils.getProperties(getClusterRepository()._list("--show-all").toString());
		for(HashMap<String,String> property : properties){
			Asserts.assertContains("gluster_service", property.keySet(), "gluster_service");
			Asserts.assertContains("memory_policy-overcommit-percent", property.keySet(), "memory_policy-overcommit-percent");
			Asserts.assertContains("memory_policy-transparent_hugepages-enabled", property.keySet(), "memory_policy-transparent_hugepages-enabled");
			Asserts.assertContains("threads_as_cores", property.keySet(), "threads_as_cores");
			Asserts.assertContains("version-major", property.keySet(), "version-major");
			Asserts.assertContains("version-minor", property.keySet(), "version-minor");
			Asserts.assertContains("virt_service", property.keySet(), "virt_service");
			assertEquals("gluster_service property value", property.get("gluster_service"), "True");
			assertEquals("virt_service property value", property.get("virt_service"), "False");
			
		}
		
	}
	
	@Test
	@Tcms("250975")
	public void testQuery(){
		List<Cluster> clusters = getClusterRepository().list("--query 'name=TestCluster'");
		assertEquals("num clusters",clusters.size() ,1);
		assertEquals("num clusters",clusters.get(0).getName(),"TestCluster");
	}
}
