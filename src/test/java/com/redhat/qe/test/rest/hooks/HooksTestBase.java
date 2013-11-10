package com.redhat.qe.test.rest.hooks;

import java.util.ArrayList;

import org.junit.After;
import org.junit.Before;

import junit.framework.Assert;

import com.redhat.qe.config.RhscConfiguration;
import com.redhat.qe.helpers.repository.HookRepoHelper;
import com.redhat.qe.helpers.ssh.HookPath;
import com.redhat.qe.helpers.ssh.HookPathFactory;
import com.redhat.qe.helpers.ssh.HooksHelper;
import com.redhat.qe.helpers.utils.Path;
import com.redhat.qe.model.Host;
import com.redhat.qe.repository.rest.HookRepository;
import com.redhat.qe.ssh.ExecSshSession;
import com.redhat.qe.test.rest.TwoHostClusterTestBase;

public abstract class HooksTestBase extends TwoHostClusterTestBase {

	protected HookPath script;

	public HooksTestBase() {
		super();
	}
	
	
	@Before
	public void before(){
		createHookScripts();
	}
	
	@After
	public void after(){
		getHooksRepo().delete(new HookRepoHelper().getHookFromHooksList(getHooksRepo(), script));
	}
	
	protected void createHookScripts(){
		script = createHookScript(getHost1(),getFilename());
		createHookScript(getHost2(),getFilename());
		
		//Sync the scripts 
		getHooksRepo().sync();
	}

	protected HookRepository getHooksRepo() {
		HookRepository hooksRepo = new HookRepository(getSession(), getHost1().getCluster());
		return hooksRepo;
	}

	protected abstract String getFilename();

	protected HookPath createHookScript(Host host, String filename) {
		HookPath hook;
		ExecSshSession host1session = ExecSshSession.fromHost(RhscConfiguration.getConfiguredHostFromBrickHost(getSession(), host));
		host1session.start();
		try{
			hook = new HooksHelper().createAsciiHook(host1session, new HookPathFactory().create(filename), "echo 'test'");
		}finally{
			host1session.stop();
		}
		return hook;
	}
	
	void ensureHookFileExistsonHost(Host host, Path expectedNewfileName) {
		ExecSshSession host1session = ExecSshSession.fromHost(RhscConfiguration.getConfiguredHostFromBrickHost(getSession(), host));
		host1session.start();
		try{
			ArrayList<HookPath> listofhookfiles = new HooksHelper().listHooks(host1session);
			listofhookfiles.get(1).getPath().equals(expectedNewfileName);
			Assert.assertTrue(listofhookfiles.contains(new HookPath(expectedNewfileName)));
		}finally{
			host1session.stop();		
		}
	}
	
	
	protected void assertScriptFilenameIsDisabled(Host host){
	    Path expectedNewfileName = script.getDirectories().add(script.getPath().last().replaceAll("^[a-zA-z]*", "K"));
		ensureHookFileExistsonHost(host, expectedNewfileName);
	}
	
	protected void assertScriptFilenameIsEnabled(Host host){
		Path expectedNewfileName = script.getDirectories().add(script.getPath().last().replaceAll("^[a-zA-z]*", "K"));
		ensureHookFileExistsonHost(host, expectedNewfileName);
	}


}