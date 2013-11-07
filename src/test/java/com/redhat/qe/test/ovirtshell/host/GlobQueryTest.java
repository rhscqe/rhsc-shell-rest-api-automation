package com.redhat.qe.test.ovirtshell.host;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.redhat.qe.annoations.Tcms;
import com.redhat.qe.model.Host;
import com.redhat.qe.test.ovirtshell.TwoHostClusterTestBase;

public class GlobQueryTest extends TwoHostClusterTestBase{
	
	
	@Before
	public void changehost1Name(){
		host1.setName("zzzzebra");
		host1 = getHostRepository().update(host1);
	}
	

	@Test
	@Tcms("250985")
	public void listGlobQueryTest(){
		String firstChar = host1.getName().substring(0, 2);
		String lastChar = host1.getName().substring(host1.getName().length() -1);
		String query = "--query \"" + firstChar + "*"+ lastChar + "\"";
		List<Host> hosts = getHostRepository().list(query);
		assertEquals(1, hosts.size());
		assertEquals(host1.getName(), hosts.get(0).getName());
	}

}
