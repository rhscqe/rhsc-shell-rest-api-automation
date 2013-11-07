package com.redhat.qe.test.rest.self;

import junit.framework.Assert;

import org.junit.Test;

import com.redhat.qe.helpers.utils.AbsolutePath;

public class PathTest {
	@Test
	public void test(){
		AbsolutePath path = new AbsolutePath("/").add("mnt").add("mymount");
		Assert.assertEquals("/mnt/mymount", path.toString());
	}
	@Test
	public void test2(){
		AbsolutePath path = AbsolutePath.fromDirs("mnt", "mymount");
		Assert.assertEquals("/mnt/mymount", path.toString());
	}
}
