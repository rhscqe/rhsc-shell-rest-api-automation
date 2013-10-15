package com.redhat.qe.helpers.cleanup;

import javax.xml.bind.JAXBException;

import org.calgb.test.performance.HttpSession;

import com.redhat.qe.config.Configuration;
import com.redhat.qe.config.RestApi;
import com.redhat.qe.config.RhscConfiguration;
import com.redhat.qe.config.ShellHost;
import com.redhat.qe.helpers.HttpSessionFactory;
import com.redhat.qe.model.Cluster;
import com.redhat.qe.model.Host;
import com.redhat.qe.model.Volume;
import com.redhat.qe.ovirt.shell.RhscShellSession;
import com.redhat.qe.repository.IVolumeRepositoryExtended;
import com.redhat.qe.repository.rest.JaxbContext;
import com.redhat.qe.repository.rest.VolumeRepository;
import com.redhat.qe.repository.rest.clibrokers.HostRepositoryBroker;
import com.redhat.qe.repository.rest.clibrokers.VolumeRepositoryBroker;
import com.redhat.qe.ssh.ChannelSshSession;
import com.redhat.qe.ssh.Credentials;
import com.redhat.qe.ssh.ExecSshSession;

import dstywho.functional.Closure2;

public class RestCleanupTool {


	public void cleanup(final Configuration config) {
		final HttpSession session = new HttpSessionFactory().createHttpSession(config.getRestApi());
		try{
			new Cleaner(){
	
				@Override
				IVolumeRepositoryExtended getVolumeRepo(Cluster cluster) {
					return new VolumeRepositoryBroker(session, cluster);
				}
	
			}.destroyAll(new com.redhat.qe.repository.rest.ClusterRepository(session), new HostRepositoryBroker(new com.redhat.qe.repository.rest.HostRepository(session)));
		}finally{
			session.stop();
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

	

		/**
		 * @param volumeRepo
		 * @param cluster
		 */



	

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
		try {
			JaxbContext.getContext().createUnmarshaller();
		} catch (JAXBException e) {
			throw new RuntimeException(e);
		}
		if (args.length == 0) {
			new RestCleanupTool().cleanup(RhscConfiguration.getConfiguration());
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

			new RestCleanupTool().cleanup(config);
		}
	}

}
