package com.redhat.qe.test.rest.brickdelete;

import java.util.ArrayList;
import java.util.Arrays;

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

public class CollectionBrickDeleteLastBrickTest extends VolumeTestBase{

	@Override
	protected Volume getVolumeToBeCreated() {
		return VolumeFactory.distributed("removeBrickCollection", getHost1(), getHost2());
	}
	
	
	@Tcms("318697")
	@Test
	public void deleteSinagle(){
		BrickRepository repo = new BrickRepository(getSession(), volume.getCluster(), volume);
		ArrayList<Brick> bricks = repo.list();
		final Brick brickToDelete = bricks.get(0);
		brickToDelete.setId(null);
		brickToDelete.setHost(getHostRepository().show(brickToDelete.getHost()));
		ResponseWrapper response = repo._collectionDelete(DeletionBrickWrapperList.fromBricks(bricks.toArray(new Brick[0])));
		Assert.assertEquals(409,response.getCode());
		response.expect("Cannot remove all the bricks from a Volume");
		
	}
	
	
}
