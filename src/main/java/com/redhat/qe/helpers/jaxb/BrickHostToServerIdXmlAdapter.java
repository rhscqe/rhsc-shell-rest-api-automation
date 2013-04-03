package com.redhat.qe.helpers.jaxb;

import javax.xml.bind.annotation.adapters.XmlAdapter;

import com.redhat.qe.model.Host;
import com.redhat.qe.model.ServerId;

public class BrickHostToServerIdXmlAdapter extends XmlAdapter<ServerId, Host> {

	// @Override
	// public String unmarshal(Host paramValueType) throws Exception {
	// return paramValueType.getId();
	// }
	//
	// @Override
	// public Host marshal(String paramBoundType) throws Exception {
	// Host host = new Host();
	// host.setId(paramBoundType);
	// return host;
	// }
//
//	@Override
//	public Host unmarshal(String paramValueType) throws Exception {
//		Host host = new Host();
//		host.setId(paramValueType);
//		return host;
//	}
//
//	@Override
//	public String marshal(Host paramBoundType) throws Exception {
//		if (paramBoundType == null)
//			return "null";
//		else
//			return paramBoundType.getId();
//	}

	@Override
	public Host unmarshal(ServerId paramValueType) throws Exception {
		Host host = new Host();
		host.setId(paramValueType.getValue());
		return host;
	}

	@Override
	public ServerId marshal(Host paramBoundType) throws Exception {
		ServerId result = new ServerId();
		if( paramBoundType != null)
			result.setValue(paramBoundType.getId());
		else
			result.setValue("null");

		return result;
	}
}
