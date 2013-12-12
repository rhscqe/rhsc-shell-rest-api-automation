package com.redhat.qe.test.rest.self;

import org.junit.Assert;
import org.junit.Test;

import com.redhat.qe.helpers.utils.FileSize;

public class FileSizeTest {
	
	@Test
	public void test(){
		Assert.assertEquals( 1024 * 16000, FileSize.megaBytes(16000).toKilobytes(),0 );
		
		
	}

	@Test
	public void test2(){
		Assert.assertEquals( 100, FileSize.megaBytes(100).toMegabytes());
		
		
	}
	@Test
	public void test3(){
		Assert.assertEquals( 1, FileSize.kiloBytes(1024).toMegabytes());
		
		
	}

	@Test
	public void test4(){
		Assert.assertEquals( 1024*10 , FileSize.Gigabytes(10).toMegabytes(), 0);
	}

}
