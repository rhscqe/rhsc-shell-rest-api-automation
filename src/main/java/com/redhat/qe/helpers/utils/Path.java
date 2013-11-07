package com.redhat.qe.helpers.utils;

import java.util.ArrayList;
import java.util.List;

import com.google.common.base.Joiner;

public class Path {
	private static final String DEFAULT_SEPARATOR = "/";
	String separator = DEFAULT_SEPARATOR;
	List<String> directories = new ArrayList<String>();
	
	
	public Path(String seperator) {
		super();
		this.separator = seperator;
	}
	public Path(Path path) {
		super();
		this.separator = path.separator;
		this.directories = new ArrayList<String>(path.directories);
	}


	public void parse(String unparsed){
		unparsed.replaceAll(separator + "*$", "");
		unparsed.replaceAll("^" + separator + "*", "");
		String[] resultDirectories = unparsed.split(separator);
		addDirs(resultDirectories);
	}
	
	public static Path from(String seperator, String unparsed){
		Path path = new Path(seperator);
		path.parse(unparsed);
		return path;
	}
	
	public static Path from(String unparsed){
		return Path.from(DEFAULT_SEPARATOR, unparsed);
	}

	public static Path fromDirs(String... parts){
		Path path = new Path(DEFAULT_SEPARATOR);
		return path.addDirs(parts);
	}
	
	public String toString(){
		return "/" + Joiner.on(this.separator).join(directories);
	}
	
	public Path addDirs(String... dirs){
		Path result = new Path(this);
		for(String dir: dirs){
			result = result.addDir(dir);
		}
		return result;
	}
	
	public Path addDir(String dir){
		return new Path(this)._addDir(dir);
	}
	
	private Path _addDir(String dir){
		directories.add(dir);
		return this;
	}
	
	

}
