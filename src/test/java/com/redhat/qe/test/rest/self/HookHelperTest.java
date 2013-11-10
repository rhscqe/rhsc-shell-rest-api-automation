package com.redhat.qe.test.rest.self;

import java.util.ArrayList;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.redhat.qe.config.RhscConfiguration;
import com.redhat.qe.helpers.ssh.FileHelper;
import com.redhat.qe.helpers.ssh.HookPath;
import com.redhat.qe.helpers.ssh.HookPathFactory;
import com.redhat.qe.helpers.ssh.HooksHelper;
import com.redhat.qe.helpers.utils.AbsolutePath;
import com.redhat.qe.model.Host;
import com.redhat.qe.ssh.ExecSshSession;

public class HookHelperTest {
	private ExecSshSession session;
	@Before
	public void before(){
		Host host = RhscConfiguration.getConfiguration().getHosts().get(0);
		session = ExecSshSession.fromHost(host);
		session.start();
	}
	
	@After
	public void after(){
		session.stop();
		
	}
	
	@Test
	public void createAndDelete(){
		HookPath hook = null;
		try{
			hook = new HooksHelper().createAsciiHook(session, new HookPathFactory().create("S90test.sh"), "happy_birthday=1");
			Assert.assertTrue(new HooksHelper().listHooks(session).contains(hook));
		}finally{
			new FileHelper().removeFile(session, new AbsolutePath(hook.getPath()));
		}
		Assert.assertFalse(new HooksHelper().listHooks(session).contains(hook));
	}
	
	
	@Test
	public void test(){
			ArrayList<HookPath> hooks = new HooksHelper().listHooks(session);
			Assert.assertTrue(hooks.size() > 4);
			for(HookPath hook: hooks){
				System.out.println("----------------------");
				System.out.println(hook.getPath().toString());
				System.out.println(hook.getPrefix().toString());
				System.out.println(hook.getName());
				System.out.println(hook.isEnabled());
			}
		
	}

}
