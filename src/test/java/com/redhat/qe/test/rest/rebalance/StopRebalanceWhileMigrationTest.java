package com.redhat.qe.test.rest.rebalance;

import java.util.ArrayList;
import java.util.Collection;

import org.junit.Assert;
import org.junit.Test;

import com.google.common.base.Function;
import com.redhat.qe.annoations.Tcms;
import com.redhat.qe.config.RhscConfiguration;
import com.redhat.qe.factories.VolumeFactory;
import com.redhat.qe.helpers.Asserts;
import com.redhat.qe.helpers.rebalance.PopulateEachBrickStrategy;
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
import com.redhat.qe.test.rest.migratebrick.MigrateTestBase;

import dstywho.timeout.Timeout;

public class StopRebalanceWhileMigrationTest extends MigrateTestBase {

	@Override
	protected Volume getVolumeToBeCreated() {
		return new VolumeFactory().distributed("startrebalwhilemig", 4, getHost1(),
				getHost2());
	}
	
	protected void populateVolume() {
	}

	@Test
	@Tcms("318690")
	public void test(){
		ArrayList<Brick> bricks = getBrickRepo().list();
		startMigrationAndWaitTilFinish(bricks.get(0), bricks.get(1));
		
		ResponseWrapper response = getVolumeRepository()._rebalance(volume);
		response.expectSimilarCode(400);
		response.expect("(?i)cannot rebalance.*task is in progress");
	}





}
