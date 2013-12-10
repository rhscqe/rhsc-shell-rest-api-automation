package com.redhat.qe.test.rest.self;

import org.junit.Assert;
import org.junit.Test;

import com.redhat.qe.helpers.utils.FileSize;

public class FileSizeTest {
	
	@Test
	public void test(){
		Assert.assertEquals( 1024 * 100, FileSize.megaBytes(100).toKilobytes());
		
		
	}

	@Test
	public void test2(){
		Assert.assertEquals( 100, FileSize.megaBytes(100).toMegabytes());
		
		
	}
	@Test
	public void test3(){
		Assert.assertEquals( 1, FileSize.kiloBytes(1024).toMegabytes());
		
		
	}

}
