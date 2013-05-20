package com.redhat.qe.repository;

import java.util.ArrayList;

import com.redhat.qe.model.Brick;
import com.redhat.qe.model.Volume;
import com.redhat.qe.ovirt.shell.RhscShellSession;
import com.redhat.qe.ssh.Response;

public class BrickRepository  {
	
	private Volume volume;
	private RhscShellSession shell;

	public BrickRepository(Volume volume, RhscShellSession shell) {
		this.shell =shell;
		this.volume = volume;
	}

	public ArrayList<Brick> list(Volume volume, String options){
		options  = (options == null) ? "" : options;
		String command = String.format("list bricks --cluster-identifier %s --glustervolume-identifier %s %s", volume.getCluster().getId(), volume.getId(), options);
		Response response = this.shell.send(command).expect("id");
		return Brick.listFromReponse(response.toString());
	}
}
