package com.redhat.qe.model;

import java.util.Random;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;

public class BrickFactory {
	
	public final static Random rand = new Random(200000L);
	
	public static Brick brick(Host host, String dir){
		Brick b = new Brick();
		b.setDir(dir);
		b.setHost(host);
		return b;
	}
	
	public static Brick brick(Host host){
		return brick(host,generateBrickDir());
	}

	/**
	 * @return 
	 * 
	 */
	private static String generateBrickDir() {
		return String.format("/tmp/%s", timestamp()  + Math.abs(rand.nextInt()));
	}

	/**
	 * @return 
	 * 
	 */
	private static String timestamp() {
		DateTime time = new DateTime();
		return time.toString(DateTimeFormat.forPattern("YYYYMMddHHmmssSSS"));
	}

}
