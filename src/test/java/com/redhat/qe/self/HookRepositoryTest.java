package com.redhat.qe.self;

import org.junit.Test;

import com.redhat.qe.repository.rest.HookRepository;
import com.redhat.qe.rest.TwoHostClusterTestBase;

public class HookRepositoryTest extends TwoHostClusterTestBase{
	
	@Test
	public void testList(){
		HookRepository repo = new HookRepository(getSession(), getHost1().getCluster());
		repo.list();
	}

}
