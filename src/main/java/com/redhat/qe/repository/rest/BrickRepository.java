package com.redhat.qe.repository.rest;

import java.util.ArrayList;
import java.util.Arrays;

import junit.framework.Assert;

import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpPost;
import org.calgb.test.performance.HttpSession;

import com.redhat.qe.helpers.Asserts;
import com.redhat.qe.model.Brick;
import com.redhat.qe.model.BrickList;
import com.redhat.qe.model.Cluster;
import com.redhat.qe.model.MigrateBrickWrapper;
import com.redhat.qe.model.MigrateBrickWrapperList;
import com.redhat.qe.model.Volume;
import com.redhat.qe.repository.rest.context.MigrateBrickJaxbContext;

public class BrickRepository extends SimpleRestRepository<Brick> {

	private Volume volume;
	private Cluster cluster;

	public BrickRepository(HttpSession session, Cluster cluster, Volume volume) {
		super(session);
		this.cluster = cluster;
		this.volume = volume;
	}

	@Override
	public String getCollectionPath() {
		return String.format("/api/clusters/%s/glustervolumes/%s/bricks",
				cluster.getId(), volume.getId());
	}
	
	@Override
	protected ArrayList<Brick> deserializeCollectionXmlToList(String raw) {
		ArrayList<Brick> result = ((BrickList) unmarshal(raw)).getBricks();
		return (result == null) ? new ArrayList<Brick>() : result;
	}

	public ArrayList<Brick> create(Brick... bricks) {
		BrickList _bricks = new BrickList();
		_bricks.getBricks().addAll(Arrays.asList(bricks));
		String xml = marshall(_bricks);
		
		HttpPost post = new PostRequestFactory().createPost(
				getCollectionPath(), xml);
		ResponseWrapper response = sendTransaction(post);
		Assert  	.assertTrue( response.getCode() == 201 || response.getCode() == 202);
		
		@SuppressWarnings("unchecked")
		ArrayList<Brick> result = ((BrickList) unmarshal(response.getBody()))
		.getBricks();
		return result;
	}
	
	@Override
	public Brick create(Brick brick){
		Brick[] bricks = new Brick[1];
		bricks[0] = brick;
		ArrayList<Brick> result = create(bricks);
		return result.get(0);
	}
	
	public ResponseWrapper _migrate(MigrateBrickWrapperList bricks){
		return sendTransaction(new PostRequestFactory()
		.createPost(getCollectionPath() + "/migrate", MigrateBrickJaxbContext.marshal(bricks)));
	}

	public ResponseWrapper _migrate(Brick... bricks){
		MigrateBrickWrapperList brickList = new MigrateBrickWrapperList();
		brickList.setBricks(new ArrayList<MigrateBrickWrapper>());
		for(Brick brick: bricks){
			brick.setHost(new HostRepository(getSession()).show(brick.getHost()));
			brickList.getBrickWrappers().add(new MigrateBrickWrapper(brick));
		}
		return _migrate(brickList);
	}
	
	
	
	
	

}
