package com.redhat.qe.repository.glustercli;

import java.util.ArrayList;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.redhat.qe.model.Volume;
import com.redhat.qe.ssh.ExecSshSession;
import com.redhat.qe.ssh.ExecSshSession.Response;

public class VolumeRebalanceRepository extends Repository{
	
	public VolumeRebalanceRepository(ExecSshSession shell) {
		super(shell);
	}

	public RebalanceStatus getRebalanceAggregateStatus(Volume volume){
		Document xml = getRebalanceStatusDocument(volume);
		Element aggregateInformation = xml.select("aggregate").first();
		return RebalanceStatus.fromElement(aggregateInformation);
	}

	public ArrayList<RebalanceStatus> getRebalanceStatus(Volume volume){
		ArrayList<RebalanceStatus> results = new ArrayList<RebalanceStatus>();
		Document xml = getRebalanceStatusDocument(volume);
		Elements nodes = xml.select("node");
		for(Element node: nodes){
			results.add( RebalanceStatus.fromElement(node));
		}
		return results;
	}
	
	

	private Document getRebalanceStatusDocument(Volume volume) {
		Response response = runCommandMultipleAttempts(String.format("gluster --mode=script volume rebalance '%s' status --xml", volume.getName()));
		Document xml = Jsoup.parse(response.getStdout());
		return xml;
	}

	

}
