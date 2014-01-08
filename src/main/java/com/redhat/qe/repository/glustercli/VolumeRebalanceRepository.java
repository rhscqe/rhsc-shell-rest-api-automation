package com.redhat.qe.repository.glustercli;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import com.redhat.qe.model.Volume;
import com.redhat.qe.ssh.ExecSshSession;
import com.redhat.qe.ssh.ExecSshSession.Response;

public class VolumeRebalanceRepository extends Repository{
	
	public VolumeRebalanceRepository(ExecSshSession shell) {
		super(shell);
	}

	public RebalanceStatus getRebalanceStatus(Volume volume){
		Response response = runCommandMultipleAttempts(String.format("gluster --mode=script volume rebalance '%s' status --xml", volume.getName()));
		Document xml = Jsoup.parse(response.getStdout());
		Element aggregateInformation = xml.select("aggregate").first();
		return RebalanceStatus.fromElement(aggregateInformation);
	}
	
	

}
