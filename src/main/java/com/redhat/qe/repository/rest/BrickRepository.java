package com.redhat.qe.repository.rest;

import java.util.ArrayList;
import java.util.Arrays;

import org.apache.http.client.methods.HttpPost;
import org.calgb.test.performance.HttpSession;
import org.testng.AssertJUnit;

import com.redhat.qe.model.Brick;
import com.redhat.qe.model.BrickList;
import com.redhat.qe.model.Cluster;
import com.redhat.qe.model.Volume;
import com.redhat.qe.model.jaxb.MigrateBrickAction;
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
		AssertJUnit  	.assertTrue( response.getCode() == 201 || response.getCode() == 202);
		
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
	
	public ResponseWrapper _migrate(MigrateBrickAction action){
		return sendTransaction(new PostRequestFactory()
		.createPost(getCollectionPath() + "/migrate", MigrateBrickJaxbContext.marshal(action)));
	}

	public ResponseWrapper _stopMigrate(MigrateBrickAction action){
		return sendTransaction(new PostRequestFactory().createPost(getCollectionPath() + "/stopmigrate", MigrateBrickJaxbContext.marshal(action)));
	}

	public ResponseWrapper _activate(MigrateBrickAction action){
		return sendTransaction(new PostRequestFactory().createPost(getCollectionPath() + "/activate", MigrateBrickJaxbContext.marshal(action)));
	}

	public MigrateBrickAction stopMigrate(MigrateBrickAction action){
		ResponseWrapper response = _stopMigrate(action);
		response.expectCode(200);
		return (MigrateBrickAction) MigrateBrickJaxbContext.unmarshal(response.getBody());
	}
	public MigrateBrickAction activate(MigrateBrickAction action){
		ResponseWrapper response = _activate(action);
		response.expectCode(200);
		return (MigrateBrickAction) MigrateBrickJaxbContext.unmarshal(response.getBody());
	}

	public MigrateBrickAction migrate(MigrateBrickAction action){
		ResponseWrapper response = _migrate(action);
		response.expectCode(200);
		return (MigrateBrickAction) MigrateBrickJaxbContext.unmarshal(response.getBody());
	}
	public ResponseWrapper _migrate(Brick... bricks){
		return _migrate(MigrateBrickAction.create(getSession(), bricks));
	}
	public ResponseWrapper _stopMigrate(Brick... bricks){
		return _stopMigrate(MigrateBrickAction.create(getSession(), bricks));
	}

	public MigrateBrickAction migrate(Brick... bricks) {
		return migrate(MigrateBrickAction.create(getSession(), bricks));
	}
	public MigrateBrickAction stopMigrate(Brick... bricks) {
		return stopMigrate(MigrateBrickAction.create(getSession(), bricks));
	}
	public MigrateBrickAction activate(Brick... bricks) {
		return stopMigrate(MigrateBrickAction.create(getSession(), bricks));
	}


	
	

}
