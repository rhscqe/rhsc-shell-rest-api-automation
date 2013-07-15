package com.redhat.qe.repository.glustercli;

import java.util.ArrayList;

import com.redhat.qe.model.Volume;
import com.redhat.qe.repository.glustercli.transformer.VolumeParser;
import com.redhat.qe.ssh.ExecSshSession;
import com.redhat.qe.ssh.ExecSshSession.Response;

public class VolumeRepository extends Repository {
	
	public VolumeRepository(ExecSshSession shell) {
		super(shell);
	}

	public ArrayList<Volume> info(){
		Response response = getShell().runCommand("gluster volume info").expectSuccessful();
		return new VolumeParser().fromListAttrGroups(response.getStdout());
	}
	
	public void  delete(Volume volume){
		getShell().runCommand(String.format("gluster --mode=script volume delete '%s'", volume.getName())).expectSuccessful();
	}

	public void stop(Volume vol) {
		getShell().runCommand(String.format("gluster --mode=script volume stop '%s' || echo \"can't stop. volume was already stopped\"", vol.getName())).expectSuccessful();
	}

}
