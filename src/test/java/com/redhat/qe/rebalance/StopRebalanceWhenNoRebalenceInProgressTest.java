package com.redhat.qe.rebalance;

import java.io.StringReader;

import javax.xml.bind.JAXBException;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.redhat.qe.annoations.Tcms;
import com.redhat.qe.factories.VolumeFactory;
import com.redhat.qe.helpers.Asserts;
import com.redhat.qe.model.Action;
import com.redhat.qe.model.Volume;
import com.redhat.qe.repository.rest.JaxbContext;
import com.redhat.qe.repository.rest.ResponseWrapper;
import com.redhat.qe.repository.rest.VolumeRepository;
import com.redhat.qe.rest.TwoHostClusterTestBase;

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
		getVolumeRepository().stop(volume);
		getVolumeRepository().destroy(volume);
	}

	@Test
	@Tcms("318692")
	public void stopReblanceWhenNoRebalanceInProgress(){
		ResponseWrapper result = getVolumeRepository().stopRebalance(volume);
		int code = result.getCode();
		ensureMethodIsA4xxMethod(code);
		Action actionResponse = (Action)unmarshalResult(result);
		Assert.assertEquals("failed", actionResponse.getStatus().getState());
		Assert.assertTrue(actionResponse.getFault().getDetail().toLowerCase().contains("rebalance is not running"));
	}

	private void ensureMethodIsA4xxMethod(int code) {
		Assert.assertTrue(code < 500 || code >= 400 );
	}

	private Action unmarshalResult(ResponseWrapper result) {
		try {
			return (Action) JaxbContext.getContext().createUnmarshaller().unmarshal(new StringReader(result.getBody()));
		} catch (JAXBException e) {
			throw new RuntimeException("failed to unmarshal the request body");
		}
	}

	/**
	 * @return
	 */
	private VolumeRepository getVolumeRepository() {
		VolumeRepository volumeRepo = getVolumeRepository(getHost1().getCluster());
		return volumeRepo;
	}


}
