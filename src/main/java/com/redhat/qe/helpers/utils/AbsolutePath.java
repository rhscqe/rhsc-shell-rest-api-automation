package com.redhat.qe.helpers.utils;

import com.google.common.base.Joiner;

public class AbsolutePath extends Path {
	
	public static AbsolutePath fromDirs(String... parts){
		return new AbsolutePath(Path.fromDirs(parts));
	}

	public AbsolutePath(Path path) {
		super(path);
	}

	public AbsolutePath(String separator) {
		super(separator);
	}

	public String toString(){
		return "/" + Joiner.on(this.separator).join(directories);
	}
	
	
	public AbsolutePath add(String... dirs){
		return new AbsolutePath(super.add(dirs));
	}


}
