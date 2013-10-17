package com.redhat.qe.repository.rhscshell;

import java.util.ArrayList;
import java.util.HashMap;

import com.redhat.qe.helpers.StringUtils;
import com.redhat.qe.model.Brick;
import com.redhat.qe.model.Volume;
import com.redhat.qe.ovirt.shell.RhscShellSession;
import com.redhat.qe.ssh.IResponse;

public class BrickRepository  {
	
	private Volume volume;
	private RhscShellSession shell;
	

	public BrickRepository(Volume volume, RhscShellSession shell) {
		this.shell =shell;
		this.volume = volume;
	}
	
	public IResponse addBrick(Brick brick){
		String command = String.format("add brick --cluster-identifier %s --glustervolume-identifier %s --brick \"brick.server_id=%s,brick.brick_dir=%s\"",
				volume.getCluster().getId(), volume.getId(), brick.getHost().getId(), brick.getDir());
		return this.shell.send(command);
	}

	public IResponse removeBrick(Brick brick){
		return _removeBrick(brick).expect("complete");
	}

	public IResponse _removeBrick( Brick brick){
		String command = String.format("remove brick %s --cluster-identifier %s --glustervolume-identifier %s",brick.getId(),
				volume.getCluster().getId(), volume.getId());
		return this.shell.send(command);
	}
	
	public Brick show( Brick brick){
		String command = String.format("show brick \"%s\" --cluster-identifier %s --glustervolume-identifier %s",brick.getName(),
				volume.getCluster().getId(), volume.getId());
		IResponse response = this.shell.send(command);
		HashMap<String, String> attrs = StringUtils.keyAttributeToHash(response.toString());
		return Brick.fromAttrs(attrs);
	}

	public ArrayList<Brick> list( String options){
		options  = (options == null) ? "" : options;
		IResponse response = _list( options).expect("id");
		return Brick.listFromReponse(response.toString());
	}
	
	public ArrayList<Brick> listAllContentTrue(){
		IResponse response = _list( "--show-all --all_content True").unexpect("error");
		return Brick.allContentlistFromReponse(response.toString());
	}

	/**
	 * @param volume
	 * @param options
	 * @return
	 */
	IResponse _list( String options) {
		String command = String.format("list bricks --cluster-identifier %s --glustervolume-identifier %s %s", volume.getCluster().getId(), volume.getId(), options);
		IResponse response = this.shell.send(command);
		return response;
	}
}
