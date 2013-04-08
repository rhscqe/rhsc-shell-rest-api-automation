package com.redhat.qe;

import org.junit.AfterClass;
import org.junit.BeforeClass;

import com.redhat.qe.config.Configuration;
import com.redhat.qe.ovirt.shell.RhscShell;
import com.redhat.qe.repository.ClusterRepository;
import com.redhat.qe.repository.HostRepository;
import com.redhat.qe.repository.VolumeRepository;
import com.redhat.qe.ssh.SshSession;

public class TestBase {

	protected static SshSession session;
	protected static RhscShell shell;
	private static HostRepository hostRepository;
	private static VolumeRepository volumeRepository;
	private static ClusterRepository clusterRepository;

	/**
	 * @return the shell
	 */
	public static RhscShell getShell() {
		return shell;
	}

	@BeforeClass
	public static void before(){
		Configuration config = Configuration.getConfiguration();
		session = SshSession.fromConfiguration(config);
		session.start();
		shell = RhscShell.fromConfiguration(session, config);
		shell.start();
		shell.connect();
	}
	
	@AfterClass
	public static void after(){
		if(session != null)
			session.stop();
	}
	
	public static HostRepository getHostRepository(){
		if(hostRepository == null){
			hostRepository = new HostRepository(getShell());
		}
		return hostRepository;
	}
	public static VolumeRepository getVolumeRepository(){
		if(volumeRepository== null){
			volumeRepository= new VolumeRepository(getShell());
		}
		return volumeRepository;
	}
	public static ClusterRepository getClusterRepository(){
		if(clusterRepository == null){
			clusterRepository = new ClusterRepository(getShell());
		}
		return clusterRepository;
		
	}
}
