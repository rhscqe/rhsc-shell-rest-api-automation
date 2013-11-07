package com.redhat.qe.helpers.utils;

import java.util.Random;

public class RandomIntGenerator {
	public final static Random rand = new Random(200000L);
	
	public static int positive(){
		return Math.abs(rand.nextInt());
	}
}
