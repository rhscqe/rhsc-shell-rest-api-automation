package com.redhat.qe.helpers.ssh;

import java.util.ArrayList;

import com.redhat.qe.helpers.utils.AbsolutePath;
import com.redhat.qe.helpers.utils.Path;
import com.redhat.qe.ssh.ExecSshSession;
import com.redhat.qe.ssh.ExecSshSession.Response;

public class HooksHelper {
	
	private static AbsolutePath HOOK_DIR = AbsolutePath.fromDirs("var","lib", "glusterd", "hooks");
	
	
	public HookPath createAsciiHook(ExecSshSession session, HookPath hook, String content){
		hook.getDirectories();
		new DirectoryHelper().createDirectory(session, new AbsolutePath(hook.getDirectories()));
		new FileHelper().createFile(session, new AbsolutePath(hook.getPath()), content);
		new FileHelper().changePermissions(session, "777",  new AbsolutePath(hook.getPath()));
		return hook;
	}
	
	
	public ArrayList<HookPath> listHooks(ExecSshSession session){
		ArrayList<HookPath> result = new ArrayList<HookPath>();
		Response response = session.runCommandAndAssertSuccess(new Find(HOOK_DIR.toString(), "-type", "f").toString());
		for(String filename : response.getStdout().split("\n")){
			result.add(new HookPath(Path.from(filename)));
		}
		return result;
		
	}

}
