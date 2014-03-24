package com.redhat.qe.test.rest.rebalance;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import com.redhat.qe.annoations.Tcms;
import com.redhat.qe.factories.VolumeFactory;
import com.redhat.qe.helpers.jaxb.ActionUnmarshaller;
import com.redhat.qe.model.Action;
import com.redhat.qe.model.Volume;
import com.redhat.qe.repository.rest.ResponseWrapper;
import com.redhat.qe.test.rest.RebalanceTestBase;

public class StartRebalanceWhenAlreadyStartedTest extends RebalanceTestBase{
	@Before
	public void startReb(){
		Action action = getVolumeRepository(getHost1().getCluster()).rebalance(volume);
	}

	@Test
	@Tcms("311348")
	public void test(){
		ResponseWrapper resp = getVolumeRepository(getHost1().getCluster())._rebalance(volume);
		Assert.assertNotSame(200, resp.getCode());
		new ActionUnmarshaller().unmarshalResult(resp);
		Assert.assertTrue(resp.getBody().contains("in progress"));
	}

	@Override
	protected Volume getVolumeToBeCreated() {
		return new VolumeFactory().distributedUneven("startrebwhenrebinprog", getHost1(), getHost2());
	}
}
