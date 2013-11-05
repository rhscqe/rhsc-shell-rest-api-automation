package com.redhat.qe.helpers.jaxb;

import java.io.StringReader;

import javax.xml.bind.JAXBException;

import com.redhat.qe.model.Action;
import com.redhat.qe.repository.rest.JaxbContext;
import com.redhat.qe.repository.rest.ResponseWrapper;

public class ActionUnmarshaller {
	public Action unmarshalResult(ResponseWrapper result) {
		try {
			return (Action) JaxbContext.getContext().createUnmarshaller().unmarshal(new StringReader(result.getBody()));
		} catch (JAXBException e) {
			throw new RuntimeException("failed to unmarshal the request body");
		}
	}
}
