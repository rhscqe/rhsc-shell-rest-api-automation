package com.redhat.qe.test.volume;



import org.junit.Test;

import com.redhat.qe.annoations.Tcms;
import com.redhat.qe.repository.GlusterOption;
import com.redhat.qe.repository.GlusterOptionValue;

public class SetOptionTest extends SingleVolumeTestBase {

	
	@Test
	@Tcms("251244")
	public void test() {
		getVolumeRepository().setOption(volume, new GlusterOptionValue(new GlusterOption("performance.cache-size"), "256MB"))
		.expect("option-value: 256MB")
		.expect("status-state: complete");
	}


}
