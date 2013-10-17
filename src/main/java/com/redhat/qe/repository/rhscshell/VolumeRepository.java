package com.redhat.qe.repository.rhscshell;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.redhat.qe.helpers.StringUtils;
import com.redhat.qe.model.Brick;
import com.redhat.qe.model.Cluster;
import com.redhat.qe.model.Volume;
import com.redhat.qe.ovirt.shell.RhscShellSession;
import com.redhat.qe.repository.IVolumeRepository;
import com.redhat.qe.repository.IVolumeRepositoryExtended;
import com.redhat.qe.ssh.IResponse;

public class VolumeRepository extends Repository<Volume> implements IVolumeRepository,IVolumeRepositoryExtended{
	
	private Cluster cluster;

	public VolumeRepository(RhscShellSession shell, Cluster cluster) {
		super(shell);
		this.cluster = cluster;
	}
	public IResponse _create(Volume volume){
		String command = String.format("add glustervolume --cluster-identifier %s --name %s --volume_type %s --stripe_count %s --replica_count %s %s"
				, volume.getCluster().getId(), volume.getName(), volume.getType(), volume.getStripe_count(), volume.getReplica_count(), bricksOptionsOnCreate(volume.getBricks()));
		return getShell().send(command);
	}

	public Volume create(Volume volume){
		return Volume.fromResponse(_create(volume).unexpect("error:"));
	}
	
	public Volume show(Volume volume){
		return Volume.fromResponse(_show(volume).unexpect("error"));
	}
	/**
	 * @param volume
	 * @return
	 */
	private IResponse _show(Volume volume) {
		return getShell().send(String.format("show glustervolume %s --cluster-identifier %s", volume.getId(), volume.getCluster().getId()));
	}
	
	
	public Volume showOrCreate(Volume volume){
		Volume show = Volume.fromResponse(getShell().send(String.format("show glustervolume %s --cluster-identifier %s", volume.getName(), volume.getCluster().getName())));
		if(show.getId() != null){
			return show;
		}else{
			return create(volume);
		}
	}
	
	private String bricksOptionsOnCreate(List<Brick> bricks){
		StringBuffer result = new StringBuffer(); 
		for(Brick brick : bricks){
			result.append(String.format("--bricks-brick '%s'",brick.toString()));
		}
		return result.toString();
	}

	public IResponse destroy(Volume volume) {
		return _destroy(volume).expect("complete");	
	}
	/**
	 * @param volume
	 * @return
	 */
	public IResponse _destroy(Volume volume) {
		return getShell().send(String.format("remove glustervolume %s --cluster-identifier %s", volume.getId(),volume.getCluster().getId()));
	}

	public Volume update(Volume entity) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public IResponse start(Volume entity){
		return getShell().send(String.format("action glustervolume %s start --cluster-identifier %s", entity.getId(), entity.getCluster().getId()))		
				.expect("complete");
	}
	
	public IResponse setOption(Volume entity, GlusterOption option, GlusterOptionValue value){
		return getShell().send(String.format("action glustervolume %s setoption --cluster-identifier %s %s %s", entity.getId(), entity.getCluster().getId(), option, value ))		
				.expect("complete");
	}
	
	public IResponse resetOption(Volume entity, GlusterOption option){
		return getShell().send(String.format("action glustervolume %s resetoption --cluster-identifier %s %s", entity.getId(), entity.getCluster().getId(), option ))		
				.expect("complete");
	}
	
	public IResponse resetAllOptions(Volume entity ){
		return getShell().send(String.format("action glustervolume %s resetalloptions --cluster-identifier %s", entity.getId(), entity.getCluster().getId()))		
				.expect("complete");
	}
	
	public IResponse stop(Volume entity){
		return _stop(entity).expect("complete");
	}

	public IResponse _stop(Volume entity){
		return getShell().send(String.format("action glustervolume %s stop --cluster-identifier %s", entity.getId(), entity.getCluster().getId()));
	}

	IResponse _list(Cluster cluster,String options){
		String formatedOptions = (options == null) ? "" : options;
		String cmd = String.format("list glustervolumes --cluster-identifier %s %s", cluster.getId(), formatedOptions);
		return getShell().send(cmd);
	}

	private ArrayList<Volume> list(Cluster cluster){
		return list(cluster, null);
	}
	
	public ArrayList<Volume> list(Cluster cluster, String options){
		IResponse response = _list(cluster, options).unexpect("error");
		Collection<String> volumesProperties = StringUtils.getPropertyKeyValueSets(response.toString());
		ArrayList<Volume> result = new ArrayList<Volume>();
		for(String volumeProperties : volumesProperties){
			result.add(Volume.fromResponse(volumeProperties));
		}
		return result;
	}
	
	public ArrayList<Brick> listBricks(Volume volume){
		return listBricks(volume, null);
	}
	
	public ArrayList<Brick> listBricks(Volume volume,String options){
		return new BrickRepository(volume, getShell()).list(options);
	}
	
	public ArrayList<Brick> listBricksAllContent(Volume volume){
		return new BrickRepository(volume, getShell()).listAllContentTrue();
	}
	
	public IResponse _listBricks(Volume volume,String options){
		return new BrickRepository(volume, getShell())._list(options);
	}
	
	public IResponse addBrick(Volume volume, Brick brick){
		return new BrickRepository(volume, getShell()).addBrick(brick);
	}
	public IResponse removeBrick(Volume volume, Brick brick){
		return new BrickRepository(volume, getShell()).removeBrick( brick);
	}
	
	public Brick showBrick(Volume volume, Brick brick){
		return new BrickRepository(volume, getShell()).show(brick);
	}
	public IResponse _removeBrick(Volume volume, Brick brick) {
		return new BrickRepository(volume, getShell())._removeBrick( brick);
	}

	private ArrayList<Volume> listAll(Cluster cluster) {
		return list(cluster, "--show-all");
	}

	public ArrayList<Volume> listAll() {
		return listAll(cluster);
	}
	public Volume createOrShow(Volume entity) {
		return null;
	}
	public boolean isExist(Volume entity) {
		return entity.getId() != null || isVolumeExistWithSameName(entity);
	}
	/**
	 * @param entity
	 * @return
	 */
	private boolean isVolumeExistWithSameName(Volume entity) {
		ArrayList<Volume> existingVolumes = list(entity.getCluster());
		for(Volume existingVol: existingVolumes){
			if(existingVol.getName().equals(entity.getName()))
				return true;
		}
		return false;
	}
	public List<Volume> list() {
		return list(cluster);
	}
	public IResponse _listAll() {
		return _list(cluster, "--show-all");
	}


}
 