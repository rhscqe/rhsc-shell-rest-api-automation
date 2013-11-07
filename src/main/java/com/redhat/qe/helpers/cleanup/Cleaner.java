package com.redhat.qe.helpers.cleanup;

import java.util.ArrayList;
import java.util.List;

import com.redhat.qe.model.Cluster;
import com.redhat.qe.model.Host;
import com.redhat.qe.model.Volume;
import com.redhat.qe.model.WaitUtil;
import com.redhat.qe.repository.IClusterRepository;
import com.redhat.qe.repository.IVolumeRepositoryExtended;
import com.redhat.qe.repository.rest.IHostRepositoryExtended;

public abstract class Cleaner {

		/**
		 * @param clusterreCleanerpo
		 * @param volumeRepo
		 * @param hostRepo
		 */
		public void destroyAll(IClusterRepository clusterrepo,  IHostRepositoryExtended hostRepo) {
			activateHostsIfNotUp(hostRepo);
			destroyAllVolumes(clusterrepo );
			destroyAllHosts(hostRepo);
			destroyAllClusters(clusterrepo);
		}

		/**
		 * @param clusterrepo
		 */
		private void destroyAllClusters(IClusterRepository clusterrepo) {
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
		private void destroyAllVolumes(IClusterRepository clusterrepo ) {
			List<Cluster> clusters = clusterrepo.list();
			for (Cluster cluster : clusters) {
				cleanUpVolumes(getVolumeRepo(cluster), clusterrepo.show(cluster));
			}
		}

		abstract IVolumeRepositoryExtended getVolumeRepo(Cluster cluster);

		/**
		 * @param hostRepo
		 */
		private void destroyAllHosts(IHostRepositoryExtended hostRepo) {
			for (Host host : hostRepo.listAll()) {
				HostCleanup.destroyHost(host, hostRepo);
			}
		}

		private void activateHostsIfNotUp(IHostRepositoryExtended hostRepo) {
			List<Host> hosts = hostRepo.listAll();
			for (Host host : hosts) {
				if (!host.getState().equals("up")) {
					hostRepo.activate(host);
					WaitUtil.waitForHostStatus(hostRepo, host, "up", 400);
				}
			}
		}
		
		private void cleanUpVolumes(IVolumeRepositoryExtended volumeRepo, Cluster cluster) {
			List<Volume> volumes = volumeRepo.listAll(); //TODO maybreak
			for (Volume volume : volumes) {
				volumeRepo._stop(volume);
				volumeRepo.destroy(volume);
			}
		}
	}
