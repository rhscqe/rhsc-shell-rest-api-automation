package com.redhat.qe.test.rest.migratebrick;

import java.util.ArrayList;

import org.junit.Test;

import com.redhat.qe.factories.VolumeFactory;
import com.redhat.qe.model.Action;
import com.redhat.qe.model.Brick;
import com.redhat.qe.model.Host;
import com.redhat.qe.model.Job;
import com.redhat.qe.model.Volume;
import com.redhat.qe.repository.JobRepository;
import com.redhat.qe.repository.rest.BrickRepository;
import com.redhat.qe.repository.rest.ResponseWrapper;
import com.redhat.qe.repository.rest.StepRepository;
public class StartMigrationDuringRebalanceTest extends MigrateTestBase{
	

	@Test
	@Tcms("321106")
	public void test(){
		Action rebalAction = getVolumeRepository(volume.getCluster()).rebalance(volume);
		try{
			
		BrickRepository brickrepo = new BrickRepository(getSession(), volume.getCluster(), volume);
		ArrayList<Brick> bricks = brickrepo.list();
		ResponseWrapper migrateAction = brickrepo._migrate(bricks.toArray(new Brick[0]));
		migrateAction.expect("(?i)in progress");
		}finally{
			getVolumeRepository().stopRebalance(volume);
		}
		
	}

	/**
	 * @param job
	 * @return
	 */
	private StepRepository getStepRepository(final Job job) {
		return new StepRepository(getSession(), job);
	}

	/**
	 * @param rebalAction
	 * @return
	 */
	private Job showJob(Job job) {
		return new JobRepository(getSession()).show(job);
	}
	
	@Override
	protected Volume getVolumeToBeCreated() {
		return VolumeFactory.distributed("rebalstatusComplete", getHosts().toArray(new Host[0]));
	}

	

}
