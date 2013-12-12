package com.redhat.qe.factories;

import java.util.Random;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;

import com.redhat.qe.helpers.utils.RandomIntGenerator;
import com.redhat.qe.helpers.utils.TimestampHelper;
import com.redhat.qe.model.Brick;
import com.redhat.qe.model.Host;

public class BrickFactory {
	

	
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
		return String.format("/bricks/%s", TimestampHelper.timestamp()  + RandomIntGenerator.positive());
	}



}
