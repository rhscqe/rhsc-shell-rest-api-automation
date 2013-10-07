package com.redhat.qe.rest;

import javax.xml.bind.JAXBException;

import org.calgb.test.performance.BuildPostException;
import org.calgb.test.performance.ProcessResponseBodyException;
import org.calgb.test.performance.RequestException;
import org.junit.Test;

import com.redhat.qe.config.RhscConfiguration;
import com.redhat.qe.model.Cluster;
import com.redhat.qe.repository.rest.JaxbContext;
import com.redhat.qe.utils.MyMarshaller;

public class ClusterTest extends TestBase{
	
	
	@Test 
	public void createDeleteTest() throws JAXBException, ProcessResponseBodyException, RequestException, BuildPostException{
		Cluster cluster = RhscConfiguration.getConfiguration().getCluster();
		String boj = MyMarshaller.marshall(JaxbContext.getContext(), cluster );
		
		cluster.setDatacenter(defaultDatatcenter());
		cluster = getClusterRepository().createOrShow(cluster);
		getClusterRepository().delete(cluster);
	}


	

}
