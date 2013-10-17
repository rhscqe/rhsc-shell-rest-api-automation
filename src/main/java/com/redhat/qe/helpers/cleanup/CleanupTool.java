package com.redhat.qe.helpers.cleanup;

import com.redhat.qe.config.Configuration;
import com.redhat.qe.config.RestApi;
import com.redhat.qe.config.RhscConfiguration;
import com.redhat.qe.config.ShellHost;
import com.redhat.qe.model.Cluster;
import com.redhat.qe.model.Host;
import com.redhat.qe.model.Volume;
import com.redhat.qe.ovirt.shell.RhscShellSession;
import com.redhat.qe.repository.IVolumeRepositoryExtended;
import com.redhat.qe.repository.rhscshell.ClusterRepository;
import com.redhat.qe.repository.rhscshell.HostRepository;
import com.redhat.qe.repository.rhscshell.VolumeRepository;
import com.redhat.qe.ssh.ChannelSshSession;
import com.redhat.qe.ssh.Credentials;
import com.redhat.qe.ssh.ExecSshSession;

import dstywho.functional.Closure2;

public class CleanupTool {

	public void cleanup(final Configuration config) {
		try {
			setupReposAndUseCleaner(config);
		} catch (Exception e) {
			e.printStackTrace();
		}

		cleanupWithGlusterCli(config);
	}

	/**
	 * @param config
	 */
	private void cleanupWithGlusterCli(final Configuration config) {
		for (Host host : config.getHosts()) {
			ExecSshSession session = new ExecSshSession(new Credentials("root",
					host.getRootPassword()), host.getAddress());
			session.start();
			try {
				com.redhat.qe.repository.glustercli.VolumeRepository volRepo = new com.redhat.qe.repository.glustercli.VolumeRepository(
						session);
				for (Volume vol : volRepo.info()) {
					volRepo.stop(vol);
					volRepo.delete(vol);
				}
			} finally {
				session.stop();
			}
		}
		for (Host host : config.getHosts()) {
			ExecSshSession session = new ExecSshSession(new Credentials("root",
					host.getRootPassword()), host.getAddress());
			session.start();
			try {
				com.redhat.qe.repository.glustercli.HostRepository hostRepository = new com.redhat.qe.repository.glustercli.HostRepository(
						session);
				for (Host peer : hostRepository.list()) {
					hostRepository.detach(peer);
				}
			} finally {
				session.stop();
			}
		}
	}

	

	private static interface RepositoryCallback {

		public void call(ClusterRepository clusterrepo, VolumeRepository volumeRepo, HostRepository hostRepo);

	}

	private void setupReposAndUseCleaner(final Configuration config ) {
		startSession(config, new Closure2<Boolean, RhscShellSession>() {

			@Override
			public Boolean act(final RhscShellSession shell) {
				final HostRepository hostRepository = new HostRepository(shell);
				final ClusterRepository clusterRepository = new ClusterRepository(
						shell);
				new Cleaner(){

					@Override
					IVolumeRepositoryExtended getVolumeRepo(Cluster cluster) {
						return new VolumeRepository(shell, cluster);
					}
				}.destroyAll(clusterRepository,  hostRepository);
				return null;
			}
		});

	}

	private void startSession(final Configuration config,
			Closure2<Boolean, RhscShellSession> c) {
		{
			ChannelSshSession session = ChannelSshSession
					.fromConfiguration(config);
			session.start();
			session.openChannel();
			final RhscShellSession shell = RhscShellSession.fromConfiguration(
					session, config);
			shell.start();
			shell.connect();
			try {
				c.call(shell);
			} finally {
				session.stopChannel();
				session.stop();
			}
		}
	}

	// shellhost hostusername hostpassword rhscusername rhscpassword
	public static void main(String[] args) {
		if (args.length == 0) {
			new CleanupTool().cleanup(RhscConfiguration.getConfiguration());
		} else if (args.length < 5) {
			System.out
					.println("need to pass arguments: clihost/ip host_username host_pass rhsc_username rhscpass");
			System.exit(1);
		} else {
			RestApi api = new RestApi("https://localhost:443/api",
					new Credentials(args[3], args[4]));
			ShellHost shell = new ShellHost(args[0], new Credentials(args[1],
					args[2]), 22);
			Configuration config = new Configuration(api, shell);

			new CleanupTool().cleanup(config);
		}
	}

}
