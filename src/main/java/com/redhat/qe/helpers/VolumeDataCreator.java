package com.redhat.qe.helpers;


import org.junit.Test;

import com.redhat.qe.config.RhscConfiguration;
import com.redhat.qe.factories.HostFactory;
import com.redhat.qe.factories.VolumeFactory;
import com.redhat.qe.model.Host;
import com.redhat.qe.model.Volume;
import com.redhat.qe.repository.rest.VolumeRepository;
import com.redhat.qe.repository.sh.DD;
import com.redhat.qe.repository.sh.Mount;
import com.redhat.qe.rest.TwoHostClusterTestBase;
import com.redhat.qe.ssh.Credentials;
import com.redhat.qe.ssh.ExecSshSession;

public class VolumeDataCreator extends TwoHostClusterTestBase{
	
	public void writeData(Volume volume ){
		Host host = volume.getBricks().get(0).getHost();
		ExecSshSession session = new ExecSshSession(new Credentials("root", host.getRootPassword()), host.getAddress());
		session.start();
		try{
			String mountPoint = "/mnt/blah";
			mountVolume(volume, host, session, mountPoint);
			session.runCommand("cd " + mountPoint);
			session.runCommandAndAssertSuccess(DD.writeRandomData("data", FileSize.megaBytes(100)).toString());
		}finally{
			session.stop();
		}

	}
	/**
	 * @param volume
	 * @param host
	 * @param session
	 * @param mountPoint
	 */
	private void mountVolume(Volume volume, Host host,
			ExecSshSession session, String mountPoint) {
		String device = String.format("%s:%s", host.getAddress(), volume.getName());
		session.runCommandAndAssertSuccess("umount  " + mountPoint);
		session.runCommandAndAssertSuccess("mkdir -p " + mountPoint);
		session.runCommandAndAssertSuccess(new Mount("glusterfs", device, mountPoint).toString());
	}
	
	@Test
	public void main(){
		Volume volume = VolumeFactory.distributed("blah", getHost1(), getHost2());
		volume = getVolumeRepository(getHost1().getCluster()).create(volume);
		writeData(volume);
	}

	@Override
	protected Host getHost1ToBeCreated() {
		return RhscConfiguration.getConfiguration().getHosts().get(0);
	}

	@Override
	protected Host getHost2ToBeCreated() {
		return RhscConfiguration.getConfiguration().getHosts().get(1);
	}


}
