package com.redhat.qe.helpers.ssh;

import com.redhat.qe.helpers.utils.AbsolutePath;
import com.redhat.qe.ssh.ExecSshSession;
import com.redhat.qe.ssh.ExecSshSession.Response;

public class DirectoryHelper {
	public void createDirectory(ExecSshSession session, AbsolutePath dir){
		if ( ! session.runCommand("stat" , dir.toString() ).isSuccessful()){
			session.runCommandAndAssertSuccess("mkdir -p " + dir);
		}
	}
	
	public Response removeDirectory(ExecSshSession session, AbsolutePath dir){
		return session.runCommandAndAssertSuccess("rm -rf " + dir.toString());
	}

}
