package com.redhat.qe.test.rest.hooks;

import java.util.ArrayList;

import junit.framework.Assert;

import com.redhat.qe.config.RhscConfiguration;
import com.redhat.qe.helpers.ssh.HookPath;
import com.redhat.qe.helpers.ssh.HookPathFactory;
import com.redhat.qe.helpers.ssh.HooksHelper;
import com.redhat.qe.helpers.utils.Path;
import com.redhat.qe.model.Host;
import com.redhat.qe.repository.rest.HookRepository;
import com.redhat.qe.ssh.ExecSshSession;
import com.redhat.qe.test.rest.TwoHostClusterTestBase;

public abstract class HookTestBase extends TwoHostClusterTestBase {

	public HookTestBase() {
		super();
	}

	protected HookPath createHookScripts(String filename) {
		HookPath script = createHookScript(getHost1(), filename);
		createHookScript(getHost2(), filename);
		
		//Sync the scripts 
		getHooksRepo().sync();
		return script;
	}

	protected HookRepository getHooksRepo() {
		HookRepository hooksRepo = new HookRepository(getSession(), getHost1().getCluster());
		return hooksRepo;
	}


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
			Assert.assertTrue(listofhookfiles.contains(new HookPath(expectedNewfileName)));
		}finally{
			host1session.stop();		
		}
	}

	protected void assertScriptFilenameIsDisabled(Host host, HookPath script) {
	    Path expectedNewfileName = script.getDirectories().add(script.getPath().last().replaceAll("^[a-zA-z]*", "K"));
		ensureHookFileExistsonHost(host, expectedNewfileName);
	}

	protected void assertScriptFilenameIsEnabled(Host host, HookPath script) {
		Path expectedNewfileName = script.getDirectories().add(script.getPath().last().replaceAll("^[a-zA-z]*", "S"));
		ensureHookFileExistsonHost(host, expectedNewfileName);
	}

}