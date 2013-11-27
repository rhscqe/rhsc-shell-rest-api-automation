package com.redhat.qe.helpers.ssh;

import junit.framework.Assert;

import com.google.common.base.Function;
import com.redhat.qe.helpers.utils.AbsolutePath;
import com.redhat.qe.model.Host;
import com.redhat.qe.model.Volume;
import com.redhat.qe.repository.sh.Mount;
import com.redhat.qe.ssh.ExecSshSession;
import com.redhat.qe.ssh.ExecSshSession.Response;

import dstywho.timeout.Duration;

public class MountHelper {

	protected static final int NUM_UMOUNT_ATTEPMTS = 10;


	/**
	 * @param mounter
	 * @param volume
	 * @param sshSession
	 * @param mountPoint
	 */
	public static void mountVolume(final Host mounter, final AbsolutePath mountPoint, final Volume volume) {
		ExecSshSession sshSession = ExecSshSession.fromHost(mounter);
		sshSession.withSession(new Function<ExecSshSession, ExecSshSession.Response>() {
			
			public Response apply(ExecSshSession session) {
				createMountPoint(mountPoint, session);
				Response result = mountVolume(mounter, mountPoint, volume, session);
				return result;
			}

			private Response mountVolume(final Host mounter, final AbsolutePath mountPoint, final Volume volume, ExecSshSession session) {
			
				Response result = null;
				if(!session.runCommandAndAssertSuccess("mount").getStdout().contains(mountPoint.toString())){
					result = session.runCommandAndAssertSuccess(new Mount("glusterfs", String.format("%s:%s", mounter.getAddress(), volume.getName()), mountPoint.toString()).toString());
				}
				return result;
			}

			private void createMountPoint(final AbsolutePath mountPoint, ExecSshSession session) {
				new DirectoryHelper().createDirectory(session, mountPoint);
			}
		});
	}
	
	
	public static void unmount(final Host mounter, final AbsolutePath mountPoint){
		ExecSshSession sshSession = ExecSshSession.fromHost(mounter);
		sshSession.withSession(new Function<ExecSshSession, ExecSshSession.Response>() {
			
			public Response apply(ExecSshSession session) {
				Assert.assertTrue(umountVolume(mountPoint, session).isSuccessful());
				return session.runCommandAndAssertSuccess("rm -rf " + mountPoint);
			}

			private Response umountVolume(final AbsolutePath mountPoint,
					ExecSshSession session) {
				Response response = null;
				for(int i =0 ; i< NUM_UMOUNT_ATTEPMTS ; i++){
					response = session.runCommand("umount " + mountPoint);
					Duration.TEN_SECONDS.sleep();
					if(response.isSuccessful()) return response;
				}
				return response;
			}
		});
		
	}
}
