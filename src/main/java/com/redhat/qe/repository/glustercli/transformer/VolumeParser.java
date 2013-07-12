package com.redhat.qe.repository.glustercli.transformer;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import com.redhat.qe.helpers.StringUtils;
import com.redhat.qe.model.Volume;

public class VolumeParser {
	
	public Volume fromAttrs(String rawattrs){
		Volume vol = new Volume();
		HashMap<String, String> attributes = StringUtils.keyAttributeToHash(rawattrs);
		vol.setName(attributes.get("Volume Name"));
		vol.setType(attributes.get("Type"));
		vol.setId(attributes.get("Volume ID"));
		//TODO 
		return vol;
	}
	
	
	public ArrayList<Volume> fromListAttrGroups(String raw){
		ArrayList<Volume> result = new ArrayList<Volume>();
		Collection<String> groups = StringUtils.getPropertyKeyValueSets(raw);
		for(String group : groups){
			result.add(fromAttrs(group));
		}
		return result;
	}
}
