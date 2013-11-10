package com.redhat.qe.helpers.ssh;

import com.redhat.qe.helpers.utils.AbsolutePath;
import com.redhat.qe.ssh.ExecSshSession;

public class DirectoryHelper {
	public void createDirectory(ExecSshSession session, AbsolutePath dir){
		if ( ! session.runCommand("stat" , dir.toString() ).isSuccessful()){
			session.runCommandAndAssertSuccess("mkdir -p " + dir);
		}
	}

}
