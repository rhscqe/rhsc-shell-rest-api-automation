package com.redhat.qe.repository;

import java.util.ArrayList;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.redhat.qe.model.Cluster;
import com.redhat.qe.model.Datacenter;

public class ClusterXmlParser {
	
	
	public Cluster parseDatacenter(String raw){
		Cluster result = new Cluster();
		result.setDescription(Jsoup.parse(raw).select("description").text());
		result.setName(Jsoup.parse(raw).select("name").text());
		result.setVirtService(Jsoup.parse(raw).select("virt_service").text().toLowerCase().contains("true"));
		result.setGlusterService((Jsoup.parse(raw).select("gluster_service").text().toLowerCase().contains("true")));
		return result;
		
	}

	public ArrayList<Cluster> parseDatacenters(String raw){
		Elements datacenterElems = Jsoup.parse(raw).select("clusters");
		ArrayList<Cluster> results = new ArrayList<Cluster>();
		for( Element datacenterelem: datacenterElems){
			results.add(parseDatacenter(datacenterelem.toString()));
		}
		return results;
		
	}

}
