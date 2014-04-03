package com.redhat.qe.helpers.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import com.google.common.base.Function;
import com.google.common.base.Predicate;
import com.redhat.qe.helpers.ssh.DirectoryHelper;
import com.redhat.qe.helpers.utils.AbsolutePath;
import com.redhat.qe.helpers.utils.CollectionUtils;
import com.redhat.qe.helpers.utils.Path;
import com.redhat.qe.model.Brick;
import com.redhat.qe.model.Host;
import com.redhat.qe.model.Volume;
import com.redhat.qe.repository.rest.BrickRepository;
import com.redhat.qe.repository.rest.VolumeRepository;
import com.redhat.qe.ssh.ExecSshSession;
import com.redhat.qe.ssh.ExecSshSession.Response;

public class BrickDirectoryDestroyer {
	private static final Logger LOG = Logger.getLogger(BrickDirectoryDestroyer.class.getName());

	public void stopAndDestroyBrickDirectories(VolumeRepository repo, ArrayList<Host> hostsWithCredentials, Volume volume){
		ArrayList<Brick> bricks = new BrickRepository(repo.getSession(), repo.getCluster() , volume).list();
		destroyBrickDirectories(repo, hostsWithCredentials, bricks);
		
	}

	public void destroyBrickDirectories(VolumeRepository repo, List<Host> hosts, ArrayList<Brick> bricks) {
		int i=0;
		int max = bricks.size()-1;
		LOG.info("deleting " + max + " brick dirs");
		for(final Brick brick: bricks){
			Host hostWithName = new com.redhat.qe.repository.rest.HostRepository(repo.getSession()).show(brick.getHost());
			Host host = getHostByName(hosts, hostWithName);
			LOG.fine("deleting brick dir " + i + "of " + max + " bricks:" + host.getName() + ";" +  brick.toString());  
			ExecSshSession.fromHost(host).withSession(new Function<ExecSshSession, ExecSshSession.Response>() {
					public Response apply(ExecSshSession session) {
						return new DirectoryHelper().removeDirectory(session, new AbsolutePath(Path.from(brick.getDir())));
					}
			});
			i++;
		}
		LOG.info("finished deleting " + max + " brick dirs");
	}

	private Host getHostByName(List<Host> hosts, final Host host) {
		return  CollectionUtils.findFirst(hosts,
				new Predicate<Host>() {

					public boolean apply(Host configHost) {
						return configHost.getName().equals( host.getName());
					}
				});
	}
}
