package com.redhat.qe.test.host;

import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.redhat.qe.annoations.Tcms;
import com.redhat.qe.config.Configuration;
import com.redhat.qe.config.RhscConfiguration;
import com.redhat.qe.factories.ClusterFactory;
import com.redhat.qe.factories.HostFactory;
import com.redhat.qe.model.Cluster;
import com.redhat.qe.model.Host;
import com.redhat.qe.model.WaitUtil;
import com.redhat.qe.repository.ClusterRepository;
import com.redhat.qe.repository.HostRepository;
import com.redhat.qe.test.OpenShellSessionTestBase;
import com.redhat.qe.test.TwoHostClusterTestBase;

public class ListHostTest extends TwoHostClusterTestBase{
	
	
	@Test
	@Tcms("250980")
	public void test(){
		List<Host> hosts = getHostRepository().list(null);
		hosts.contains(host1);
		hosts.contains(host2);
	}

}
