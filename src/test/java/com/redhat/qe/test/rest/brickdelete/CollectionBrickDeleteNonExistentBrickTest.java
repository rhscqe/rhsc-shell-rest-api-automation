package com.redhat.qe.test.rest.brickdelete;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.UUID;

import junit.framework.Assert;

import org.junit.Test;

import com.google.common.base.Predicate;
import com.google.common.collect.Collections2;
import com.redhat.qe.annoations.Tcms;
import com.redhat.qe.factories.VolumeFactory;
import com.redhat.qe.model.Brick;
import com.redhat.qe.model.BrickList;
import com.redhat.qe.model.Volume;
import com.redhat.qe.model.jaxb.DeletionBrickWrapperList;
import com.redhat.qe.repository.rest.BrickRepository;
import com.redhat.qe.repository.rest.ResponseWrapper;
import com.redhat.qe.test.rest.VolumeTestBase;

public class CollectionBrickDeleteNonExistentBrickTest extends VolumeTestBase{

	@Override
	protected Volume getVolumeToBeCreated() {
		return new VolumeFactory().distributed("removeBrickCollection", getHost1(), getHost2());
	}
	
	
	@Tcms("318699")
	@Test
	public void test(){
		BrickRepository repo = new BrickRepository(getSession(), volume.getCluster(), volume);
		ArrayList<Brick> bricks = repo.list();
		final Brick brickToDelete = bricks.get(0);
		brickToDelete.setId(UUID.randomUUID().toString());
		ResponseWrapper response = repo._collectionDelete(DeletionBrickWrapperList.fromBricks(brickToDelete));
		Assert.assertEquals(400,response.getCode());
		response.expect("(?i)Invalid gluster Brick");
		
	}
	
	
}
