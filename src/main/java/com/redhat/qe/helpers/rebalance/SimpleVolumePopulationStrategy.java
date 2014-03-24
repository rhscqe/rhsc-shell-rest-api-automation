package com.redhat.qe.helpers.rebalance;

import org.apache.log4j.Logger;

import com.redhat.qe.helpers.MountedVolume;
import com.redhat.qe.helpers.utils.AbsolutePath;
import com.redhat.qe.helpers.utils.FileSize;
import com.redhat.qe.repository.sh.DD;
import com.redhat.qe.ssh.ExecSshSession;

public class SimpleVolumePopulationStrategy extends VolumePopulationStrategy{
	private static final Logger LOG = Logger.getLogger(SimpleVolumePopulationStrategy.class);
	public static final FileSize MAX_TOTAL_DATA_WRITTEN = FileSize.Gigabytes(10);
	private MountedVolume mountedVolume;


	public SimpleVolumePopulationStrategy(MountedVolume mountedVolume){
		this.mountedVolume = mountedVolume;
	}

	@Override
	protected void populate(FileSize fileSizeToPopulateWith, FileSize maxDataToWrite) {
		FileSize amountOfDataWritten = FileSize.megaBytes(0);
		LOG.info("starting to populate volume");
		while(amountOfDataWritten.toMegabytes() < maxDataToWrite.toMegabytes()){
			writeFile(getFileCreationStrategy(), mountedVolume, fileSizeToPopulateWith);
			amountOfDataWritten = FileSize.megaBytes(amountOfDataWritten.toMegabytes() + fileSizeToPopulateWith.toMegabytes());
			LOG.info(String.format("%sMB/%sMB written to volume", amountOfDataWritten.toMegabytes(), maxDataToWrite.toMegabytes()));
		}
		LOG.info("finished populating volume");
	}
	
	
	public FileCreator getFileCreationStrategy(){
		return new FileCreator(){

			@Override
			public void createFile(ExecSshSession sshSession, AbsolutePath path, FileSize size) {
				sshSession.runCommandAndAssertSuccess( DD.writeZeros(path.toString(), size).toString() );
			}};
	}

}
