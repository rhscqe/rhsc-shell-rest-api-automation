package com.redhat.qe.helpers.rebalance;

import java.util.ArrayList;

import org.apache.log4j.Logger;
import org.calgb.test.performance.HttpSession;

import com.google.common.base.Function;
import com.redhat.qe.config.RhscConfiguration;
import com.redhat.qe.factories.VolumeFactory;
import com.redhat.qe.helpers.repository.HostHelper;
import com.redhat.qe.helpers.rest.HttpSessionFactory;
import com.redhat.qe.helpers.rest.WithEachBrick;
import com.redhat.qe.helpers.ssh.MountHelper;
import com.redhat.qe.helpers.utils.AbsolutePath;
import com.redhat.qe.helpers.utils.FileSize;
import com.redhat.qe.helpers.utils.Null;
import com.redhat.qe.helpers.utils.RandomIntGenerator;
import com.redhat.qe.helpers.utils.TimestampHelper;
import com.redhat.qe.model.Brick;
import com.redhat.qe.model.Cluster;
import com.redhat.qe.model.Host;
import com.redhat.qe.model.Volume;
import com.redhat.qe.repository.rest.ClusterRepository;
import com.redhat.qe.repository.rest.HostRepository;
import com.redhat.qe.repository.rest.VolumeRepository;
import com.redhat.qe.repository.sh.DD;
import com.redhat.qe.ssh.ExecSshSession;
import com.redhat.qe.ssh.ExecSshSession.Response;

public class BrickPopulator {

	private static final Logger LOG = Logger.getLogger(BrickPopulator.class);
	
	public static void main(String[] args){
		HttpSession session = new HttpSessionFactory().createHttpSession(RhscConfiguration.getConfiguration().getRestApi());
		Cluster cluster = new ClusterRepository(session).createOrShow(RhscConfiguration.getConfiguration().getCluster());
		Host h1 = new HostHelper().createAndWaitForUp(new HostRepository(session),RhscConfiguration.getConfiguration().getHosts().get(0));
		Host h2 = new HostHelper().createAndWaitForUp(new HostRepository(session),RhscConfiguration.getConfiguration().getHosts().get(1));
		VolumeRepository volumeRepository = new VolumeRepository(session, cluster);
		Volume volume = volumeRepository.createOrShow(VolumeFactory.distributed("red", h1, h2));
		volumeRepository._start(volume);
		AbsolutePath mountPoint = AbsolutePath.fromDirs("mnt", volume.getName());
		Host mounter = RhscConfiguration.getConfiguration().getHosts().get(0);
		MountHelper.mountVolume(mounter, mountPoint, volume);
		new BrickPopulator().createDataForEachBrick(session, cluster,volume, mounter, mountPoint);
	}


	/**
	 * @param session
	 * @param cluster
	 * @param mounter
	 * @param volume
	 */
	public void createDataForEachBrick(final HttpSession session, Cluster cluster,  final Volume volume,final Host mounter, final AbsolutePath mountPoint) {
		ArrayList<Brick> bricks = new com.redhat.qe.repository.rest.BrickRepository(session, cluster, volume).list();
		
		WithEachBrick.withEachBrick(bricks, session, new Function<WithEachBrick, Null>() {
			
			public Null apply(WithEachBrick brickinfo) {
				LOG.info("populating brick:" + brickinfo.getBrick().getName());
				while (getListofFilesForBrick(brickinfo.getBrick(), brickinfo.getHost(), session).getStdout().isEmpty()) {
					writeRandomFile(mountPoint, mounter, volume);
				}
				LOG.info("finished populating brick:" + brickinfo.getBrick().getName());
				return Null.NULL;
			}
		});
	}

	private AbsolutePath writeRandomFile(AbsolutePath mountPoint, Host mounter, Volume volume) {
		AbsolutePath file = mountPoint.add(randomFileName());
		ExecSshSession sshSession = ExecSshSession.fromHost(mounter);
		sshSession.start();
		try {
//			sshSession.runCommandAndAssertSuccess(DD.writeZeros(file.toString(), FileSize.megaBytes(50)).toString());
//			sshSession.runCommandAndAssertSuccess("echo \"$(date)$RANDOM\" > " + file.toString());
			sshSession.runCommandAndAssertSuccess(DD.writeRandomData(file.toString(), FileSize.megaBytes(100)).toString());
		} finally {
			sshSession.stop();
 		}
		return file;
	}
	


	private static String randomFileName() {
		return TimestampHelper.timestamp() + "" + (RandomIntGenerator.positive() + "").substring(0, 5);
	}



	private static Response getListofFilesForBrick(final Brick brick, Host host, HttpSession session) {
		Host configuredhost = RhscConfiguration.getConfiguredHostFromBrickHost(session, brick.getHost()); 
		ExecSshSession hostSshSession = ExecSshSession.fromHost(configuredhost);
		return hostSshSession.withSession(new Function<ExecSshSession, ExecSshSession.Response>() { public Response apply(ExecSshSession s) { return  s.runCommandAndAssertSuccess("ls " + brick.getDir()); } });
	}



}
