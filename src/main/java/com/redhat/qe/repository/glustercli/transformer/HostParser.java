package com.redhat.qe.repository.glustercli.transformer;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import com.redhat.qe.helpers.StringUtils;
import com.redhat.qe.model.Host;
import com.redhat.qe.model.Volume;

public class HostParser {
	
	public Host fromAttrs(String rawattrs){
		Host host = new Host();
		HashMap<String, String> attributes = StringUtils.keyAttributeToHash(rawattrs);
		host.setAddress(attributes.get("Hostname"));
		return host;
	}
	
	
	public ArrayList<Host> fromListAttrGroups(String raw){
		ArrayList<Host> result = new ArrayList<Host>();
		if(raw.contains("Number of Peers: 0"))
			return result;
		Collection<String> groups = StringUtils.getPropertyKeyValueSets(raw);
		for(String group : groups){
			result.add(fromAttrs(group));
		}
		return result;
	}
}
