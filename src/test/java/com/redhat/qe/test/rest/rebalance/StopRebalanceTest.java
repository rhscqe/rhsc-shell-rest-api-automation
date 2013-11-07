package com.redhat.qe.test.rest.rebalance;

import org.junit.Before;
import org.junit.Test;

import com.redhat.qe.repository.rest.ResponseWrapper;
import com.redhat.qe.test.rest.RebalanceTestBase;

public class StopRebalanceTest extends RebalanceTestBase{
	@Before
	public void startRebalance(){
		getVolumeRepository(getHost1().getCluster()).rebalance(volume);
		//TODO this needs to be waited on to start
	}
	
	@Test
	public void test(){
		ResponseWrapper result = getVolumeRepository(getHost1().getCluster()).stopRebalance(volume);

		System.out.println("ZZZZZZZZZZZZZZZZZZZ");
		System.out.println(result.getBody());
		System.out.println(result.getCode());
	}

}
