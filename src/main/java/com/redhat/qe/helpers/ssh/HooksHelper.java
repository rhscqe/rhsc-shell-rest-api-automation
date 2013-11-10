package com.redhat.qe.helpers.ssh;

import java.util.ArrayList;

import com.redhat.qe.helpers.utils.Path;
import com.redhat.qe.helpers.utils.AbsolutePath;
import com.redhat.qe.ssh.ExecSshSession;
import com.redhat.qe.ssh.ExecSshSession.Response;

public class HooksHelper {
	
	private static AbsolutePath HOOK_DIR = AbsolutePath.fromDirs("var","lib", "glusterd", "hooks");
	
	
	public HookPath createAsciiHook(ExecSshSession session,String version, String event, String lifecycle, String filename, String content){
		HookPath hook = new HookPath(HOOK_DIR.add(version).add(event).add(lifecycle).add(filename));
		hook.getDirectories();
		new DirectoryHelper().createDirectory(session, new AbsolutePath(hook.getDirectories()));
		new FileHelper().createFile(session, new AbsolutePath(hook.getPath()), content);
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
