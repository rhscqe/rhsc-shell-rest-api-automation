package com.redhat.qe.test.rest.self;

import junit.framework.Assert;

import org.junit.Test;

import com.redhat.qe.helpers.utils.FileSize;
import com.redhat.qe.repository.sh.DD;

public class DDTest {
	
	@Test
	public void test(){
		Assert.assertEquals("dd of=to count=10 if=from bs=1024K", DD.writeData("from", "to", "1024K", 10).toString());
	}

	@Test
	public void test2(){
		Assert.assertEquals("dd of=to count=500 if=/dev/urandom bs=1024k", DD.writeRandomData("to", FileSize.megaBytes(500)).toString());
	}

}
