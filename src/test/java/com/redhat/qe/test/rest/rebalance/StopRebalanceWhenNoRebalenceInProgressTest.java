package com.redhat.qe.test.rest.rebalance;

import java.io.StringReader;

import javax.xml.bind.JAXBException;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.redhat.qe.annoations.Tcms;
import com.redhat.qe.factories.VolumeFactory;
import com.redhat.qe.helpers.Asserts;
import com.redhat.qe.helpers.jaxb.ActionUnmarshaller;
import com.redhat.qe.model.Action;
import com.redhat.qe.model.Volume;
import com.redhat.qe.repository.rest.JaxbContext;
import com.redhat.qe.repository.rest.ResponseWrapper;
import com.redhat.qe.repository.rest.VolumeRepository;
import com.redhat.qe.test.rest.TwoHostClusterTestBase;

public class StopRebalanceWhenNoRebalenceInProgressTest extends TwoHostClusterTestBase{
	
	private Volume volume;
	@Before
	public void setupVolume(){
		VolumeRepository volumeRepo = getVolumeRepository();
		volume = volumeRepo.createOrShow(VolumeFactory.distributed("red", getHost1(), getHost2()));
		volumeRepo._start(volume);
	}

	/**
	 * @return
	 */
	@After
	public void afterme(){
		if(volume.getId() !=null){
			getVolumeRepository().stop(volume);
			getVolumeRepository().destroy(volume);
		}
	}

	@Test
	@Tcms("318692")
	public void stopReblanceWhenNoRebalanceInProgress(){
		ResponseWrapper result = getVolumeRepository()._stopRebalance(volume);
		int code = result.getCode();
		Asserts.asertCodeIsInRangeInclusive(code, 400, 499);
		Action actionResponse = (Action)new ActionUnmarshaller().unmarshalResult(result);
		Assert.assertEquals("failed", actionResponse.getStatus().getState());
		Assert.assertTrue(actionResponse.getFault().getDetail().toLowerCase().contains("rebalance is not running"));
	}



	/**
	 * @return
	 */
	private VolumeRepository getVolumeRepository() {
		VolumeRepository volumeRepo = getVolumeRepository(getHost1().getCluster());
		return volumeRepo;
	}


}
