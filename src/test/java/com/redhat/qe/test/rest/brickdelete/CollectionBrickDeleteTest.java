package com.redhat.qe.test.rest.brickdelete;

import java.util.ArrayList;

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

public class CollectionBrickDeleteTest extends VolumeTestBase{

	@Override
	protected Volume getVolumeToBeCreated() {
		return VolumeFactory.distributed("removeBrickCollection", getHost1(), getHost2());
	}
	
	
	@Tcms("318694")
	@Test
	public void deleteSinagle(){
		BrickRepository repo = new BrickRepository(getSession(), volume.getCluster(), volume);
		ArrayList<Brick> bricks = repo.list();
		final Brick brickToDelete = bricks.get(0);
		brickToDelete.setId(null);
		brickToDelete.setHost(getHostRepository().show(brickToDelete.getHost()));
		ResponseWrapper response = repo._collectionDelete(DeletionBrickWrapperList.fromBricks(brickToDelete));
		Assert.assertEquals(200,response.getCode());
		assertBrickDeleted(repo, brickToDelete);
	}
	
	@Tcms("318695")
	@Test
	public void deleteMultiple(){
		BrickRepository repo = new BrickRepository(getSession(), volume.getCluster(), volume);
		ArrayList<Brick> bricks = repo.list();
		final Brick brickToDelete = bricks.get(0);
		final Brick brickToDelete2 = bricks.get(1);
		ResponseWrapper response = repo._collectionDelete(DeletionBrickWrapperList.fromBricks(brickToDelete, brickToDelete2));
		Assert.assertEquals(200,response.getCode());
		assertBrickDeleted(repo, brickToDelete);
		assertBrickDeleted(repo, brickToDelete2);
	}


	/**
	 * @param repo
	 * @param brickToDelete
	 */
	private void assertBrickDeleted(BrickRepository repo,
			final Brick brickToDelete) {
		ArrayList<Brick> bricksAferDelete = repo.list();
		Assert.assertTrue(Collections2.filter(bricksAferDelete, new Predicate<Brick>() {

			public boolean apply(Brick input) {
				return input.getId().equals(brickToDelete.getHost().getId()) && input.getDir().equals(brickToDelete.getDir());
			}
		}).isEmpty());
	}
	
	@Tcms("318694")
	@Test
	public void testUsingId(){
		BrickRepository repo = new BrickRepository(getSession(), volume.getCluster(), volume);
		ArrayList<Brick> bricks = repo.list();
		Brick brickToDelete = bricks.get(0);
		ResponseWrapper response = repo._collectionDelete(DeletionBrickWrapperList.fromBricks(brickToDelete));
		Assert.assertEquals(200,response.getCode());
		assertBrickDeleted(repo, brickToDelete);
	}

}
