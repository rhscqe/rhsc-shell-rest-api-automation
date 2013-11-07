package com.redhat.qe.test.rest.self;

import org.junit.Test;

import com.redhat.qe.repository.rest.HookRepository;
import com.redhat.qe.test.rest.TwoHostClusterTestBase;

public class HookRepositoryTest extends TwoHostClusterTestBase{
	
	@Test
	public void testList(){
		HookRepository repo = new HookRepository(getSession(), getHost1().getCluster());
		repo.list();
	}

}
