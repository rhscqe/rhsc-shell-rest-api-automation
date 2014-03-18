package com.redhat.qe.helpers.ssh;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.google.common.base.Function;
import com.redhat.qe.helpers.MountedVolume;
import com.redhat.qe.helpers.repository.BrickDirectoryDestroyer;
import com.redhat.qe.model.Brick;
import com.redhat.qe.model.Host;
import com.redhat.qe.repository.rest.BrickRepository;
import com.redhat.qe.repository.rest.VolumeRepository;
import com.redhat.qe.ssh.ExecSshSession;
import com.redhat.qe.ssh.ExecSshSession.Response;
import com.redhat.reportengine.server.dbmap.Server;

public class MountedVolumeHelper {
	private static final Logger LOG = Logger.getLogger(MountedVolumeHelper.class);
	private void deleteAllFilesInMountedVolume(VolumeRepository volumeRepo, MountedVolume mountedVolume ) {
		if(volumeRepo.show(mountedVolume.getVolume()).getStatus().equals("up"))
			cleanVolumeUp(mountedVolume);
	}

	protected void cleanVolumeUp(final MountedVolume mountedVolume ){
		ExecSshSession sshSession = ExecSshSession.fromHost(mountedVolume.getMounter());
		sshSession.withSession(new Function<ExecSshSession, ExecSshSession.Response>() {
			
			public Response apply(ExecSshSession session) {
				return session.runCommandAndAssertSuccess("rm -rf " + mountedVolume.getMountPoint().add("*").toString() );
			}
			
		});
			
	}
	
	

	public void cleanupMountedVolume(VolumeRepository repo, MountedVolume mountedVolume){
		deleteAllFilesInMountedVolume(repo, mountedVolume);
	}
	

}
