package com.redhat.qe.helpers;


import java.util.Collection;

import org.junit.Assert;


public class Asserts {
	
	
	public static void assertContains(String description, String target, String substring){
		Assert.assertTrue(String.format("%s: the target string <<<%s>>> does not contain substring <<<%s>>>", description,target, substring), target.contains(substring));
	}

	public static void assertContains(String description, Collection<String> target, String substring) {
		Assert.assertTrue(String.format("%s: the target  <<<%s>>> does not contain substring <<<%s>>>", description,target.toString(), substring), target.contains(substring));
	}

}
