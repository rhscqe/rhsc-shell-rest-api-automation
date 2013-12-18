package com.redhat.qe.factories;

import com.redhat.qe.helpers.utils.AbsolutePath;
import com.redhat.qe.helpers.utils.RandomIntGenerator;
import com.redhat.qe.helpers.utils.TimestampHelper;
import com.redhat.qe.model.Brick;
import com.redhat.qe.model.Host;

public class BrickFactory {
	

	private AbsolutePath baseDir;

	public BrickFactory(AbsolutePath baseDir){
		this.baseDir = baseDir;
	}
	
	public BrickFactory(){
		this(AbsolutePath.fromDirs("bricks"));
	}
	
	
	public static Brick brick(Host host, String dir){
		Brick b = new Brick();
		b.setDir(dir);
		b.setHost(host);
		return b;
	}
	
	public Brick brick(Host host){
		return brick(host,generateBrickDir());
	}

	/**
	 * @return 
	 * 
	 */
	private String generateBrickDir() {
		return baseDir.add(TimestampHelper.timestamp()  + RandomIntGenerator.positive()).toString();
	}



}
