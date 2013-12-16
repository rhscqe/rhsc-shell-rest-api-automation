package com.redhat.qe.helpers.rebalance;

import java.io.FileWriter;
import java.util.ArrayList;

import org.apache.log4j.Logger;
import org.calgb.test.performance.HttpSession;

import com.google.common.base.Function;
import com.redhat.qe.config.RhscConfiguration;
import com.redhat.qe.factories.VolumeFactory;
import com.redhat.qe.helpers.repository.HostHelper;
import com.redhat.qe.helpers.rest.HttpSessionFactory;
import com.redhat.qe.helpers.rest.WithEachBrick;
import com.redhat.qe.helpers.ssh.FileHelper;
import com.redhat.qe.helpers.ssh.MountHelper;
import com.redhat.qe.helpers.utils.AbsolutePath;
import com.redhat.qe.helpers.utils.FileNameHelper;
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

import dstywho.functional.Closure;

public class BrickPopulator {
	
	public static abstract class FileCreator{
		public abstract void createFile(ExecSshSession sshSession, AbsolutePath path, FileSize size);
		
	}
	
	public static final FileSize MAX_TOTAL_DATA_WRITTEN = FileSize.Gigabytes(16);

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
//		MountHelper.unmount(mounter, mountPoint);
	}



	private FileSize fileSizeToPopulateWith;
	private FileSize maxDataToWrite;
	
	public BrickPopulator(FileSize fileSizeToPopulateWith, FileSize maxDataToWrite) {
		this.fileSizeToPopulateWith = fileSizeToPopulateWith ;
		this.maxDataToWrite = maxDataToWrite;
	}
	public BrickPopulator(FileSize fileSizeToPopulateWith) {
		this(fileSizeToPopulateWith, MAX_TOTAL_DATA_WRITTEN);
	}
	

	public BrickPopulator() {
		this(FileSize.megaBytes(500));
	}


	/**
	 * @param session
	 * @param cluster
	 * @param mounter
	 * @param volume
	 */
	public void createDataForEachBrick(final HttpSession session, Cluster cluster,  final Volume volume,final Host mounter, final AbsolutePath mountPoint) {
		ArrayList<Brick> bricks = new com.redhat.qe.repository.rest.BrickRepository(session, cluster, volume).list();
		createDataForEachBrickBy(new FileCreator(){

			@Override
			public void createFile(ExecSshSession sshSession, AbsolutePath path, FileSize size) {
				sshSession.runCommandAndAssertSuccess(DD.writeRandomData(path.toString(),size).toString());
			}}, session, cluster, volume, mounter, mountPoint);
		
	}

	public void createDataForEachBrickBy(FileCreator fileCreator, final HttpSession session, Cluster cluster,  final Volume volume,final Host mounter, final AbsolutePath mountPoint) {
		ArrayList<Brick> bricks = new com.redhat.qe.repository.rest.BrickRepository(session, cluster, volume).list();
		
		long totalMegaBytesWritten = 0;
		int numFilesCreated = 0;
		for (final Brick brick : bricks) {
			Host host= RhscConfiguration.getConfiguredHostFromBrickHost(session, brick.getHost()); 
			LOG.info("populating brick:" + brick.getName());
			
			while (getListofFilesForBrick(brick, brick.getHost(), session).getStdout().isEmpty() 
					&& totalMegaBytesWritten < maxDataToWrite.toMegabytes()) {
				writeFile(fileCreator, mountPoint, mounter, fileSizeToPopulateWith);

				numFilesCreated++;
				totalMegaBytesWritten = totalMegaBytesWritten + fileSizeToPopulateWith.toMegabytes();
				LOG.info(totalMegaBytesWritten + "MBs written");
			}
			
			LOG.info("finished populating brick:" + brick.getName());
		}
	}

	private AbsolutePath writeFile(FileCreator fileCreator, AbsolutePath mountPoint, Host mounter, FileSize fileSize) {
		AbsolutePath file = mountPoint.add(FileNameHelper.randomFileName());
		ExecSshSession sshSession = ExecSshSession.fromHost(mounter);
		sshSession.start();
		try {
			fileCreator.createFile(sshSession, file, fileSize);
		} finally {
			sshSession.stop();
		}
		return file;
	}

	private AbsolutePath writeRandomFile(AbsolutePath mountPoint, Host mounter, Volume volume, FileSize fileSize) {
		AbsolutePath file = mountPoint.add(FileNameHelper.randomFileName());
		ExecSshSession sshSession = ExecSshSession.fromHost(mounter);
		sshSession.start();
		try {
			sshSession.runCommandAndAssertSuccess(DD.writeRandomData(file.toString(),fileSize ).toString());
		} finally {
			sshSession.stop();
 		}
		return file;
	}
	



	private static Response getListofFilesForBrick(final Brick brick, Host host, HttpSession session) {
		Host configuredhost = RhscConfiguration.getConfiguredHostFromBrickHost(session, brick.getHost()); 
		ExecSshSession hostSshSession = ExecSshSession.fromHost(configuredhost);
		return hostSshSession.withSession(new Function<ExecSshSession, ExecSshSession.Response>() { public Response apply(ExecSshSession s) { return  s.runCommandAndAssertSuccess("ls " + brick.getDir()); } });
	}



}
