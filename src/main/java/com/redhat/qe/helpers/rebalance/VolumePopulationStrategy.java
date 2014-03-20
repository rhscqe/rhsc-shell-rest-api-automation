package com.redhat.qe.helpers.rebalance;

import com.redhat.qe.helpers.MountedVolume;
import com.redhat.qe.helpers.utils.AbsolutePath;
import com.redhat.qe.helpers.utils.FileNameHelper;
import com.redhat.qe.helpers.utils.FileSize;
import com.redhat.qe.ssh.ExecSshSession;

public abstract class VolumePopulationStrategy {

	public static final FileSize MAX_TOTAL_DATA_WRITTEN = FileSize.Gigabytes(16);
	FileSize _fileSizeToPopulateWith =   FileSize.megaBytes(500);
	FileSize _maxDataToWrite = MAX_TOTAL_DATA_WRITTEN;

	public void populate(){
		populate(_fileSizeToPopulateWith,_maxDataToWrite);
		
	}
	

	protected abstract void populate(FileSize fileSizeToPopulateWith, FileSize maxDataToWrite);
	
	public static abstract class FileCreator{
		public abstract void createFile(ExecSshSession sshSession, AbsolutePath path, FileSize size);
	}

	protected AbsolutePath writeFile(FileCreator fileCreator, MountedVolume volume, FileSize fileSize) {
		AbsolutePath file = volume.getMountPoint().add(FileNameHelper.randomFileName());
		ExecSshSession sshSession = ExecSshSession.fromHost(volume.getMounter());
		sshSession.start();
		try {
			fileCreator.createFile(sshSession, file, fileSize);
		} finally {
			sshSession.stop();
		}
		return file;
	}

	
	

}
