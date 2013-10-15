package com.redhat.qe.repository.glustercli;

import java.util.ArrayList;

import org.apache.commons.lang.ArrayUtils;
import org.apache.log4j.Logger;

import com.redhat.qe.model.Brick;
import com.redhat.qe.model.Volume;
import com.redhat.qe.model.WaitUtil;
import com.redhat.qe.repository.IVolumeRepository;
import com.redhat.qe.repository.glustercli.transformer.VolumeParser;
import com.redhat.qe.repository.glustercli.transformer.VolumeParser.BrickClientDetails;
import com.redhat.qe.repository.glustercli.transformer.VolumeParser.BrickMemoryDetails;
import com.redhat.qe.ssh.ExecSshSession;
import com.redhat.qe.ssh.ExecSshSession.Response;

public class VolumeRepository extends Repository {
	private static Logger LOG = Logger.getLogger(VolumeRepository.class);
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
	
	public ArrayList<Brick> status_detail(Volume volume){
		ArrayList<Brick> result = new ArrayList<Brick>();
		Response response = statusSubcommand(volume, "detail");
		String[] rawBrickDataForEachBrick = response.getStdout().split("---(-)*");
		rawBrickDataForEachBrick = removeFirst(rawBrickDataForEachBrick);
		for(String rawBrickData : rawBrickDataForEachBrick){
			Brick brick = new VolumeParser().fromVolumeDeailsSingleBrickData(rawBrickData);		
			result.add(brick);
		}
		return result;
	}

	private String[] removeFirst(String[] rawBrickDataForEachBrick) {
		rawBrickDataForEachBrick = (String[]) ArrayUtils.remove(rawBrickDataForEachBrick, 0); //first element is not brick data.
		return rawBrickDataForEachBrick;
	}
	public ArrayList<BrickClientDetails> status_client(Volume volume){
		ArrayList<BrickClientDetails> result = new ArrayList<BrickClientDetails>();
		Response response = statusSubcommand(volume, "client");
		String[] rawBrickDataForEachBrick = response.getStdout().split("---------------------+");
		rawBrickDataForEachBrick = removeFirstAndLast(rawBrickDataForEachBrick);
		for(String rawBrickData : rawBrickDataForEachBrick){
			BrickClientDetails brick = new VolumeParser().fromVolumeStatusClientSingleBrickData(rawBrickData);		
			result.add(brick);
		}
		return result;
	}

	public ArrayList<BrickMemoryDetails> memStatus(Volume volume) {
		ArrayList<BrickMemoryDetails> result = new ArrayList<BrickMemoryDetails>();
		Response response = statusSubcommand(volume, "mem");
		LOG.debug("gluster memory stats for volume: " + volume.getName());
		LOG.debug(response.getStdout());
		String[] rawBrickDataForEachBrick = response.getStdout().split("---------------------------------------+");
		rawBrickDataForEachBrick = removeFirstAndLast(rawBrickDataForEachBrick);
		for(String rawBrickData : rawBrickDataForEachBrick){
			BrickMemoryDetails brick = new VolumeParser().fromVolumeMemoryStatusFromSingleBrickRaw(rawBrickData);		
			result.add(brick);
		}
		return result;
	}

	private String[] removeFirstAndLast(String[] rawBrickDataForEachBrick) {
		rawBrickDataForEachBrick = removeFirst(rawBrickDataForEachBrick);
		rawBrickDataForEachBrick = (String[]) ArrayUtils.remove(rawBrickDataForEachBrick, rawBrickDataForEachBrick.length -1); //last is empty
		return rawBrickDataForEachBrick;
	}
	
	private Response statusSubcommand(Volume volume, String subcommand){
		ArrayList<BrickClientDetails> result = new ArrayList<BrickClientDetails>();
		return getShell().runCommand(String.format("gluster --mode=script volume status '%s' %s", volume.getName(), subcommand)).expectSuccessful();
	}
	
	

}
