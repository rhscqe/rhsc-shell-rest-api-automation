package com.redhat.qe.helpers.rebalance;

import java.util.ArrayList;

import org.calgb.test.performance.HttpSession;

import com.google.common.base.Function;
import com.google.common.base.Predicate;
import com.redhat.qe.config.RhscConfiguration;
import com.redhat.qe.factories.VolumeFactory;
import com.redhat.qe.helpers.CollectionUtils;
import com.redhat.qe.helpers.FileSize;
import com.redhat.qe.helpers.HttpSessionFactory;
import com.redhat.qe.helpers.Path;
import com.redhat.qe.helpers.RandomIntGenerator;
import com.redhat.qe.helpers.TimestampHelper;
import com.redhat.qe.helpers.repository.HostHelper;
import com.redhat.qe.model.Brick;
import com.redhat.qe.model.Cluster;
import com.redhat.qe.model.Host;
import com.redhat.qe.model.Volume;
import com.redhat.qe.repository.rest.ClusterRepository;
import com.redhat.qe.repository.rest.HostRepository;
import com.redhat.qe.repository.rest.VolumeRepository;
import com.redhat.qe.repository.rhscshell.BrickRepository;
import com.redhat.qe.repository.sh.DD;
import com.redhat.qe.repository.sh.Mount;
import com.redhat.qe.ssh.Credentials;
import com.redhat.qe.ssh.ExecSshSession;
import com.redhat.qe.ssh.ExecSshSession.Response;

public class BrickFileCreator {

	
	public static void main(String[] args){
		HttpSession session = new HttpSessionFactory().createHttpSession(RhscConfiguration.getConfiguration().getRestApi());
			Cluster cluster = new ClusterRepository(session).createOrShow(RhscConfiguration.getConfiguration().getCluster());
			Host h1 = new HostHelper().createAndWaitForUp(new HostRepository(session),RhscConfiguration.getConfiguration().getHosts().get(0));
			Host h2 = new HostHelper().createAndWaitForUp(new HostRepository(session),RhscConfiguration.getConfiguration().getHosts().get(1));
			VolumeRepository volumeRepository = new VolumeRepository(session, cluster);
			Volume volume = volumeRepository.createOrShow(VolumeFactory.distributed("redhat", h1, h2));
			volumeRepository._start(volume);
			ArrayList<Brick> bricks = new com.redhat.qe.repository.rest.BrickRepository(session, cluster, volume).list();
			for(final Brick brick: bricks){
				Host host = getConfiguredHostFromBrickHost(session, brick);

				ExecSshSession hostSshSession = ExecSshSession.fromHost(host);
				while(getListofFilesForBrick(brick, hostSshSession).getStdout().isEmpty()){
					mountVolumeAndWriteFile(session, h1, volume);
				}

			}
		
		
	}

	private static void mountVolumeAndWriteFile(HttpSession session, Host h1,
			Volume volume) {
		Host firstHost = RhscConfiguration.getConfiguration().getHosts().get(0);
		ExecSshSession sshSession = new ExecSshSession(new Credentials("root", firstHost.getRootPassword()), firstHost.getAddress());
		sshSession.start();
		try {
			Path mountPoint = Path.fromDirs("mnt", volume.getName());
			sshSession.runCommandAndAssertSuccess("mkdir -p " + mountPoint);
			if(!sshSession.runCommandAndAssertSuccess("mount").getStdout().contains(mountPoint.toString())){
				sshSession.runCommandAndAssertSuccess(new Mount("glusterfs", String.format("%s:%s", h1.getAddress(), volume.getName()), mountPoint.toString()).toString());
			}
			sshSession.runCommandAndAssertSuccess(DD.writeRandomData(mountPoint.addDir(TimestampHelper.timestamp() + "" + RandomIntGenerator.positive()).toString(), FileSize.megaBytes(10)).toString());

		} finally {
			sshSession.stop();
		}
	}

	private static Response getListofFilesForBrick(final Brick brick,
			ExecSshSession hostSshSession) {
		return hostSshSession.withSession(new Function<ExecSshSession, ExecSshSession.Response>() { public Response apply(ExecSshSession s) { return  s.runCommandAndAssertSuccess("ls " + brick.getDir()); } });
	}

	private static Host getConfiguredHostFromBrickHost(HttpSession session,
			Brick brick) {
		final Host host = new HostRepository(session).show(brick.getHost());
		Host configuredHost = CollectionUtils.findFirst(RhscConfiguration.getConfiguration().getHosts(), new Predicate<Host>() {

				public boolean apply(Host configHost) {
					return configHost.getName().equals(host.getName());
				}
		});
		return configuredHost;
	}

}
