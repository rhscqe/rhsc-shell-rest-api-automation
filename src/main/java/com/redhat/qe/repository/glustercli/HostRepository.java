package com.redhat.qe.repository.glustercli;

import java.util.ArrayList;

import com.redhat.qe.model.Host;
import com.redhat.qe.repository.glustercli.transformer.HostParser;
import com.redhat.qe.ssh.ExecSshSession;
import com.redhat.qe.ssh.ExecSshSession.Response;

public class HostRepository extends Repository {

	/**
	 * @param shell
	 */
	public HostRepository(ExecSshSession shell) {
		super(shell);
	}
	
	public ArrayList<Host> status(){
		Response response = getShell().runCommand("gluster peer status").expectSuccessful();
		return new HostParser().fromListAttrGroups(response.getStdout());
	}
	public ArrayList<Host> peerStatus(){
		return status();
	}
	public ArrayList<Host> index(){
		return status();
	}
	public ArrayList<Host> list(){
		return status();
	}
	
	public void detach(Host host){
		getShell().runCommand(String.format("gluster --mode=script peer detach %s", host.getAddress())).expectSuccessful();
	}


}
