package com.redhat.qe.utils;

import java.io.StringWriter;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

public class MyMarshaller {
	public static String marshall(JAXBContext context, Object obj ) throws JAXBException{
		Marshaller m = context.createMarshaller();
		m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
		StringWriter writer = new StringWriter();
		m.marshal(obj, writer);
		return writer.toString();
	}

}
