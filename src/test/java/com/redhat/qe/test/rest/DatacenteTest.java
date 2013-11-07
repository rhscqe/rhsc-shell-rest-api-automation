package com.redhat.qe.test.rest;

import java.util.ArrayList;

import javax.xml.bind.JAXBException;
import javax.xml.stream.FactoryConfigurationError;
import javax.xml.stream.XMLStreamException;

import org.junit.Assert;

import org.calgb.test.performance.BuildPostException;
import org.calgb.test.performance.ProcessResponseBodyException;
import org.calgb.test.performance.RequestException;
import org.junit.Test;

import com.redhat.qe.model.Datacenter;
import com.redhat.qe.repository.rest.DatacenterRepository;

public class DatacenteTest extends RestTestBase{
	@Test
	public void list() throws ProcessResponseBodyException, RequestException, JAXBException, XMLStreamException, FactoryConfigurationError, BuildPostException{
		ArrayList<Datacenter> datacenters = getDatacenterRepository().list();
		Assert.assertTrue(datacenters.size() > 0);
	}

}
