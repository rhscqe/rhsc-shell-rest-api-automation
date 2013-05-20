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
	
	public Response addBrick(Volume volume, Brick brick){
		String command = String.format("add brick --cluster-identifier %s --glustervolume-identifier %s --brick \"brick.server_id=%s,brick.brick_dir=%s\"",
				volume.getCluster().getId(), volume.getId(), brick.getHost().getId(), brick.getDir());
		return this.shell.send(command);
	}

	public ArrayList<Brick> list(Volume volume, String options){
		options  = (options == null) ? "" : options;
		Response response = _list(volume, options).expect("id");
		return Brick.listFromReponse(response.toString());
	}

	/**
	 * @param volume
	 * @param options
	 * @return
	 */
	Response _list(Volume volume, String options) {
		String command = String.format("list bricks --cluster-identifier %s --glustervolume-identifier %s %s", volume.getCluster().getId(), volume.getId(), options);
		Response response = this.shell.send(command);
		return response;
	}
}
