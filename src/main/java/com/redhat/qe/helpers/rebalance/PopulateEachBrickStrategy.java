package com.redhat.qe.helpers.rebalance;

import java.util.List;

import org.apache.log4j.Logger;

import com.redhat.qe.config.ConfiguredHosts;
import com.redhat.qe.helpers.MountedVolume;
import com.redhat.qe.helpers.utils.AbsolutePath;
import com.redhat.qe.helpers.utils.FileNameHelper;
import com.redhat.qe.helpers.utils.FileSize;
import com.redhat.qe.model.Brick;
import com.redhat.qe.model.Host;
import com.redhat.qe.repository.sh.DD;
import com.redhat.qe.ssh.ExecSshSession;

public class PopulateEachBrickStrategy extends VolumePopulationStrategy{
	

	

	private static final Logger LOG = Logger.getLogger(PopulateEachBrickStrategy.class);
	
//	public static void main(String[] args){
//		HttpSession session = new HttpSessionFactory().createHttpSession(RhscConfiguration.getConfiguration().getRestApi());
//		Cluster cluster = new ClusterRepository(session).createOrShow(RhscConfiguration.getConfiguration().getCluster());
//		Host h1 = new HostHelper().createAndWaitForUp(new HostRepository(session),RhscConfiguration.getConfiguration().getHosts().get(0));
//		Host h2 = new HostHelper().createAndWaitForUp(new HostRepository(session),RhscConfiguration.getConfiguration().getHosts().get(1));
//		VolumeRepository volumeRepository = new VolumeRepository(session, cluster);
//		Volume volume = volumeRepository.createOrShow(new VolumeFactory().distributed("red", h1, h2));
//		volumeRepository._start(volume);
//		AbsolutePath mountPoint = AbsolutePath.fromDirs("mnt", volume.getName());
//		Host mounter = RhscConfiguration.getConfiguration().getHosts().get(0);
//		MountHelper.mountVolume(mounter, mountPoint, volume);
//		new BrickPopulator().createDataForEachBrick(session, cluster,volume, mounter, mountPoint);
////		MountHelper.unmount(mounter, mountPoint);
//	}



	private List<Brick> bricks;

	private ConfiguredHosts hosts;

	private MountedVolume mountedVolume;
	
	public PopulateEachBrickStrategy(List<Brick> bricks, ConfiguredHosts hosts, MountedVolume mountedVolume) {
		this.bricks = bricks;
		this.hosts =hosts;
		this.mountedVolume = mountedVolume;
	}


	public void createDataForEachBrickBy(FileCreator fileCreator, FileSize fileSizeToPopulateWith, FileSize maxDataToWrite) {
		
		long totalMegaBytesWritten = 0;
		int numFilesCreated = 0;
		for(int i=0; i < bricks.size(); i++){
			Brick brick = bricks.get(i);
			Host host = hosts.getHost(brick);
			LOG.info(String.format("populating brick(%s/%s): %s:%s", i+1, bricks.size(), host.getAddress(), brick.getDir()));
			
			while (new BrickFiles(new BrickSshSessionFactory(hosts)).listFiles(brick).isEmpty() && totalMegaBytesWritten < maxDataToWrite.toMegabytes()) {

				writeFile(fileCreator, mountedVolume, fileSizeToPopulateWith);

				numFilesCreated++;
				totalMegaBytesWritten = totalMegaBytesWritten + fileSizeToPopulateWith.toMegabytes();
				LOG.info(totalMegaBytesWritten + "MBs written");
			}
			
			LOG.info("finished populating brick:" + brick.getName());
		}
	}



	@Override
	protected void populate(FileSize fileSizeToPopulateWith, FileSize maxDataToWrite) {
		createDataForEachBrickBy(new FileCreator(){

			@Override
			public void createFile(ExecSshSession sshSession, AbsolutePath path, FileSize size) {
				sshSession.runCommandAndAssertSuccess(DD.writeRandomData(path.toString(),size).toString());
			}}, fileSizeToPopulateWith, maxDataToWrite);
	}

//	private AbsolutePath writeRandomFile(AbsolutePath mountPoint, Host mounter, Volume volume, FileSize fileSize) {
//		AbsolutePath file = mountPoint.add(FileNameHelper.randomFileName());
//		ExecSshSession sshSession = ExecSshSession.fromHost(mounter);
//		sshSession.start();
//		try {
//			sshSession.runCommandAndAssertSuccess(DD.writeRandomData(file.toString(),fileSize ).toString());
//		} finally {
//			sshSession.stop();
// 		}
//		return file;
//	}
//	

	



}
