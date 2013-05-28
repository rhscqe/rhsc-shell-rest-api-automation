package com.redhat.qe.helpers;

import java.util.ArrayList;
import java.util.List;

import com.jcraft.jsch.ChannelShell;
import com.redhat.qe.config.Configuration;
import com.redhat.qe.config.RhscConfiguration;
import com.redhat.qe.model.Cluster;
import com.redhat.qe.model.Host;
import com.redhat.qe.model.Volume;
import com.redhat.qe.model.WaitUtil;
import com.redhat.qe.ovirt.shell.RhscShellSession;
import com.redhat.qe.repository.ClusterRepository;
import com.redhat.qe.repository.HostRepository;
import com.redhat.qe.repository.VolumeRepository;
import com.redhat.qe.ssh.SshSession;

import dstywho.functional.Closure2;

public class CleanupTool {
	
	public void cleanup(){
		repositories(new Cleaner());
	}
	
	
	private final class Cleaner implements RepositoryCallback {
		public void call(ClusterRepository clusterrepo, VolumeRepository volumeRepo, HostRepository hostRepo) {
			destroyAll(clusterrepo, volumeRepo, hostRepo);
		}

		/**
		 * @param clusterrepo
		 * @param volumeRepo
		 * @param hostRepo
		 */
		private void destroyAll(ClusterRepository clusterrepo, VolumeRepository volumeRepo, HostRepository hostRepo) {
			activateHostsIfNotUp(hostRepo);
			destroyAllVolumes(clusterrepo, volumeRepo);
			destroyAllHosts(hostRepo);
			destroyAllClusters(clusterrepo);
		}

		/**
		 * @param clusterrepo
		 */
		private void destroyAllClusters(ClusterRepository clusterrepo) {
			List<Cluster> clusters = clusterrepo.list();
			for (Cluster cluster : clusters) {
				if (!cluster.getName().contains("Default")) {
					clusterrepo.destroy(cluster);
				}
			}
		}

		/**
		 * @param clusterrepo
		 * @param volumeRepo
		 * @return
		 */
		private void destroyAllVolumes(ClusterRepository clusterrepo, VolumeRepository volumeRepo) {
			List<Cluster> clusters = clusterrepo.list();
			for(Cluster cluster: clusters){
				cleanUpVolumes(volumeRepo, cluster);
			}
		}

		/**
		 * @param hostRepo
		 */
		private void destroyAllHosts(HostRepository hostRepo) {
			for(Host host: hostRepo.list(null)){
				HostCleanup.destroyHost(host, hostRepo);
			}
		}

		private void activateHostsIfNotUp(HostRepository hostRepo) {
			List<Host> hosts = hostRepo.list("--show-all");
			for(Host host:hosts){
				if(!host.getState().equals("up")){
					hostRepo.activate(host);
					WaitUtil.waitForHostStatus(hostRepo, host, "up", 400);
				}
			}
		}

		/**
		 * @param volumeRepo
		 * @param cluster
		 */
		private void cleanUpVolumes(VolumeRepository volumeRepo, Cluster cluster) {
			ArrayList<Volume> volumes = volumeRepo.list(cluster,"--show-all");
			for (Volume volume :volumes){
				volumeRepo._stop(volume);
				volumeRepo.destroy(volume);
			}
		}
	}


	private static interface RepositoryCallback{
		
		public void call(ClusterRepository clusterrepo, VolumeRepository volumeRepo, HostRepository hostRepo);
			
		
	}
	
	private void repositories(final RepositoryCallback doRepoStuff){
		startSession(new Closure2<Boolean, RhscShellSession>() {
			
			@Override
			public Boolean act(RhscShellSession shell) {
				final HostRepository hostRepository = new HostRepository(shell);
				final VolumeRepository volumeRepository = new VolumeRepository(shell);
				final ClusterRepository clusterRepository = new ClusterRepository(shell);
				doRepoStuff.call(clusterRepository, volumeRepository, hostRepository);
				return true;
			}
		});
		
	}
	
	private void startSession(Closure2<Boolean,RhscShellSession> c){
		{
			Configuration config = RhscConfiguration.getConfiguration();
			SshSession session = SshSession.fromConfiguration(config);
			session.start();
			session.openChannel();
			final RhscShellSession shell = RhscShellSession.fromConfiguration(session, config);
			shell.start();
			shell.connect();
			try{
				c.call(shell);
			}finally{
				session.stopChannel();
				session.stop();
			}
		}
	}
	
	public static void main(String[] args){
		new CleanupTool().cleanup();
	}
		

}
