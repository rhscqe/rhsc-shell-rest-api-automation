package com.redhat.qe.repository.glustercli;

import java.util.ArrayList;

import com.redhat.qe.model.Volume;
import com.redhat.qe.repository.glustercli.transformer.VolumeParser;
import com.redhat.qe.ssh.ExecSshSession;
import com.redhat.qe.ssh.ExecSshSession.Response;

public class VolumeRepository extends Repository {
	
	/**
	 * @param shell
	 */
	public VolumeRepository(ExecSshSession shell) {
		super(shell);
		// TODO Auto-generated constructor stub
	}

	public ArrayList<Volume> info(){
		Response response = getShell().runCommand("gluster volume info").expectSuccessful();
		return new VolumeParser().fromListAttrGroups(response.getStdout());
	}
	
	public void  delete(Volume volume){
		getShell().runCommand(String.format("gluster --mode=script volume delete '%s' || echo crap", volume.getName())).expectSuccessful();
	}

}
