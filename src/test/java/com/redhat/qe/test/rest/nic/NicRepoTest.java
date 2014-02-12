package com.redhat.qe.test.rest.nic;

import java.util.ArrayList;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Test;

import com.redhat.qe.config.RhscConfiguration;
import com.redhat.qe.helpers.cleanup.RestCleanupTool;
import com.redhat.qe.model.Nic;
import com.redhat.qe.repository.rest.NicRepository;
import com.redhat.qe.test.rest.TwoHostClusterTestBase;

public class NicRepoTest extends TwoHostClusterTestBase{
	
	@Test
	public void test(){
		ArrayList<Nic> nics = new NicRepository(getSession(), getHost1()).list();
		Assert.assertTrue(nics.size() > 0);
		
	}

}
