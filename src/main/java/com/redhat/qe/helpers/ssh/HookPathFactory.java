package com.redhat.qe.helpers.ssh;

import com.redhat.qe.helpers.utils.AbsolutePath;

public class HookPathFactory {
	private static AbsolutePath HOOK_DIR = AbsolutePath.fromDirs("var","lib", "glusterd", "hooks");
	
	public HookPath create(String version, String event, String lifecycle, String filename ){
		return new HookPath(HOOK_DIR.add(version).add(event).add(lifecycle).add(filename));
	}
	
	public HookPath create(String filename){
		return create("1", "test", "pre", filename);
	}

}
