package com.redhat.qe.test.rest.volume;

import org.junit.Test;

import com.redhat.qe.factories.BrickFactory;
import com.redhat.qe.factories.VolumeFactory;
import com.redhat.qe.model.Volume;
import com.redhat.qe.repository.rest.BrickRepository;
import com.redhat.qe.test.rest.VolumeTestBase;

public class AddBrickTest extends VolumeTestBase{

	@Override
	protected Volume getVolumeToBeCreated() {
		return new VolumeFactory().distributed("addBrickTest", host1, host2);
	}

	@Test
	public void test(){
		BrickRepository repo = new BrickRepository(getSession(), volume.getCluster(), volume);
		repo.create(new  BrickFactory().brick(getHost2()));
	}
}
