package com.redhat.qe.test.rest.self;

import junit.framework.Assert;

import org.junit.Test;

import com.redhat.qe.helpers.utils.Path;

public class PathTest {
	@Test
	public void test(){
		Path path = new Path("/").addDir("mnt").addDir("mymount");
		Assert.assertEquals("/mnt/mymount", path.toString());
	}
	@Test
	public void test2(){
		Path path = Path.fromDirs("mnt", "mymount");
		Assert.assertEquals("/mnt/mymount", path.toString());
	}
}
