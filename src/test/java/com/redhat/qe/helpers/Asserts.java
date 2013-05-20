package com.redhat.qe.helpers;


import java.util.ArrayList;
import java.util.Collection;

import org.junit.Assert;

import com.redhat.qe.model.Volume;


public class Asserts {
	
	
	public static void assertContains(String description, String target, String substring){
		Assert.assertTrue(String.format("%s: the target string <<<%s>>> does not contain <<<%s>>>", description,target, substring), target.contains(substring));
	}

	public static <T> void assertContains(String description, Collection<T> target, T entry) {
		Assert.assertTrue(String.format("%s: the target  <<<%s>>> does not contain <<<%s>>>", description,target.toString(), entry), target.contains(entry));
	}
	
	public static <T> void assertDoesntContain(String description, Collection<T> target, T entry) {
		Assert.assertTrue(String.format("%s: the target  <<<%s>>> does contain <<<%s>>>", description,target.toString(), entry), !target.contains(entry));
	}



}
