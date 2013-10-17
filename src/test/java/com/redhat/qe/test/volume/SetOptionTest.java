package com.redhat.qe.test.volume;



import static org.junit.Assert.*;

import org.junit.Test;

import com.redhat.qe.annoations.Tcms;
import com.redhat.qe.model.Volume;
import com.redhat.qe.repository.rhscshell.GlusterOption;
import com.redhat.qe.repository.rhscshell.GlusterOptionValue;

public class SetOptionTest extends SingleVolumeTestBase {

	
	private static final GlusterOptionValue VALUE = new GlusterOptionValue("256MB");
	private static final GlusterOption OPTION = new GlusterOption("performance.cache-size");

	@Test
	@Tcms("251244")
	public void test() {
		getVolumeRepository().setOption(volume, OPTION, VALUE)
		.expect("option-value: 256MB")
		.expect("status-state: complete");
		
		Volume options = getVolumeRepository().show(volume);
		assertEquals(VALUE, options.getVolumeOptions().get(OPTION));
	}
	
	@Test
	@Tcms("251245")
	public void resetTest() {
		GlusterOption option = OPTION;
		
		getVolumeRepository().setOption(volume, OPTION, VALUE)
		.expect("option-value: 256MB")
		.expect("status-state: complete");
		
		getVolumeRepository().resetOption(volume,option);
		
		
		assertEquals(null, getVolumeRepository().show(volume).getVolumeOptions());
		
	}
	
	@Test
	@Tcms("251247")
	public void resetAllTest() {
		
		getVolumeRepository().setOption(volume, OPTION, VALUE)
		.expect("option-value: 256MB")
		.expect("status-state: complete");
		
		getVolumeRepository().resetAllOptions(volume);
		
		assertEquals(null, getVolumeRepository().show(volume).getVolumeOptions());
		
	}


}
