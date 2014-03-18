package com.redhat.qe.helpers;

import com.redhat.qe.helpers.utils.AbsolutePath;
import com.redhat.qe.model.Host;
import com.redhat.qe.model.Volume;

public class MountedVolume {
	
	AbsolutePath mountPoint;
	Host mounter;
	Volume volume;
	
	/**
	 * @param mountPoint
	 * @param mounter
	 * @param volume
	 */
	public MountedVolume(AbsolutePath mountPoint, Host mounter, Volume volume) {
		super();
		this.mountPoint = mountPoint;
		this.mounter = mounter;
		this.volume = volume;
	}
	public AbsolutePath getMountPoint() {
		return mountPoint;
	}
	public void setMountPoint(AbsolutePath mountPoint) {
		this.mountPoint = mountPoint;
	}
	public Host getMounter() {
		return mounter;
	}
	public void setMounter(Host mounter) {
		this.mounter = mounter;
	}
	public Volume getVolume() {
		return volume;
	}
	public void setVolume(Volume volume) {
		this.volume = volume;
	}

	
	
}
