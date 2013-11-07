package com.redhat.qe.helpers.ssh;

import com.google.common.base.Function;
import com.redhat.qe.helpers.utils.Path;
import com.redhat.qe.model.Host;
import com.redhat.qe.model.Volume;
import com.redhat.qe.repository.sh.Mount;
import com.redhat.qe.ssh.ExecSshSession;
import com.redhat.qe.ssh.ExecSshSession.Response;

public class MountHelper {

	/**
	 * @param mounter
	 * @param volume
	 * @param sshSession
	 * @param mountPoint
	 */
	public static void mountVolume(final Host mounter, final Path mountPoint, final Volume volume) {
		ExecSshSession sshSession = ExecSshSession.fromHost(mounter);
		sshSession.withSession(new Function<ExecSshSession, ExecSshSession.Response>() {
			
			public Response apply(ExecSshSession session) {
				createMountPoint(mountPoint, session);
				Response result = mountVolume(mounter, mountPoint, volume, session);
				return result;
			}

			private Response mountVolume(final Host mounter, final Path mountPoint, final Volume volume, ExecSshSession session) {
			
				Response result = null;
				if(!session.runCommandAndAssertSuccess("mount").getStdout().contains(mountPoint.toString())){
					result = session.runCommandAndAssertSuccess(new Mount("glusterfs", String.format("%s:%s", mounter.getAddress(), volume.getName()), mountPoint.toString()).toString());
				}
				return result;
			}

			private void createMountPoint(final Path mountPoint,
					ExecSshSession session) {
				if ( ! session.runCommand("stat" , mountPoint.toString() ).isSuccessful()){
					session.runCommandAndAssertSuccess("mkdir -p " + mountPoint);
				}
			}
		});
	}
	
	
	public static void unmount(final Host mounter, final Path mountPoint){
		ExecSshSession sshSession = ExecSshSession.fromHost(mounter);
		sshSession.withSession(new Function<ExecSshSession, ExecSshSession.Response>() {
			
			public Response apply(ExecSshSession session) {
				session.runCommandAndAssertSuccess("umount " + mountPoint);
				return session.runCommandAndAssertSuccess("rm -rf " + mountPoint);
			}
		});
		
	}
}
