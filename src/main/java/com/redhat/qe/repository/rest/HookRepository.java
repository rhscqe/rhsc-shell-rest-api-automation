package com.redhat.qe.repository.rest;

import java.util.ArrayList;

import javax.xml.bind.JAXBException;

import junit.framework.Assert;

import org.calgb.test.performance.HttpSession;

import com.redhat.qe.helpers.mechanize.GuiSync;
import com.redhat.qe.model.Action;
import com.redhat.qe.model.Cluster;
import com.redhat.qe.model.GlusterHookList;
import com.redhat.qe.model.Hook;
import com.redhat.qe.model.Host;
import com.redhat.qe.model.HostList;
import com.redhat.qe.model.jaxb.HookResolutionAction;

public class HookRepository extends SimpleRestRepository<Hook>{
	
	private Cluster cluster;

	public HookRepository(HttpSession session, Cluster cluster){
		super(session);
		this.cluster = cluster;
	}

	@Override
	public String getCollectionPath() {
		return String.format("/api/clusters/%s/glusterhooks", cluster.getId() );
		
	}

	@Override
	protected ArrayList<Hook> deserializeCollectionXmlToList(String raw) {
		ArrayList<Hook> result = ((GlusterHookList) unmarshal(raw)).getHooks();
		return (result == null) ? new ArrayList<Hook>() : result; 
	}
	
	public ResponseWrapper enable(Hook hook){
		return customAction(hook, getCollectionPath(), "enable");
	}
	public ResponseWrapper disable(Hook hook){
		return customAction(hook, getCollectionPath(), "disable");
	}

	public ResponseWrapper _resolve(Hook hook, HookResolutionAction resolution){
		try {
			return sendTransaction(new PostRequestFactory()
			.createPost(customActionPath(hook, getCollectionPath(), "resolve"), com.redhat.qe.helpers.jaxb.MyMarshaller.marshall(HookResolutionJaxbContext.getContext(), resolution)));
		} catch (JAXBException e) {
			throw new RuntimeException(e);
		}
	}
	
	public Action resolve(Hook hook, HookResolutionAction resolution){
		ResponseWrapper result = _resolve(hook, resolution);
		Assert.assertEquals(200, result.getCode());
		return (Action) unmarshal(result.getBody());
	}
	
	

	public void sync() {
		// TODO Auto-generated method stub
		// FIXME
//		System.out.println("sync not implemented.");		
		try{
			GuiSync.sync();
		}catch(Exception e){
			throw new RuntimeException(e);
		}
	}
	

}
