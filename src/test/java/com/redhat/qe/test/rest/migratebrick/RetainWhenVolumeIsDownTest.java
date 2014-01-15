package com.redhat.qe.test.rest.migratebrick;

import java.util.ArrayList;
import java.util.Collection;

import org.junit.Assert;
import org.junit.Test;

import com.google.common.base.Function;
import com.redhat.qe.annoations.Tcms;
import com.redhat.qe.config.RhscConfiguration;
import com.redhat.qe.factories.VolumeFactory;
import com.redhat.qe.helpers.Asserts;
import com.redhat.qe.helpers.rebalance.BrickPopulator;
import com.redhat.qe.helpers.repository.JobRepoHelper;
import com.redhat.qe.helpers.repository.StepsRepositoryHelper;
import com.redhat.qe.model.Brick;
import com.redhat.qe.model.Job;
import com.redhat.qe.model.Step;
import com.redhat.qe.model.Volume;
import com.redhat.qe.model.gluster.VolumeStatusOutput;
import com.redhat.qe.model.jaxb.MigrateBrickAction;
import com.redhat.qe.repository.JobRepository;
import com.redhat.qe.repository.glustercli.VolumeXmlRepository;
import com.redhat.qe.repository.rest.BrickRepository;
import com.redhat.qe.repository.rest.ResponseWrapper;
import com.redhat.qe.repository.rest.StepRepository;
import com.redhat.qe.ssh.ExecSshSession;
import com.redhat.qe.ssh.ExecSshSession.Response;
import com.redhat.qe.test.rest.PopulatedVolumeTestBase;

import dstywho.timeout.Timeout;

public class RetainWhenVolumeIsDownTest extends MigrateTestBase {

	@Override
	protected Volume getVolumeToBeCreated() {
		return VolumeFactory.distributed("startbriciikmigrate", 4, getHost1(),
				getHost2());
	}
	
	protected void populateVolume() {
	}

	@Test
	@Tcms("325558")
	public void test(){
		ArrayList<Brick> bricks = getBrickRepo().list();
		startMigrationAndWaitTilFinish(bricks.get(0), bricks.get(1));
		
		getVolumeRepository().stop(volume);
		
		ResponseWrapper response = getBrickRepo()._activate(bricks.get(0),bricks.get(1));
		response.expectSimilarCode(400);
		response.expect("down");
		
	}


}
