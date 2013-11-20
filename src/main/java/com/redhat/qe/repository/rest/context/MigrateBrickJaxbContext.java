package com.redhat.qe.repository.rest.context;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import com.redhat.qe.helpers.jaxb.MyMarshaller;
import com.redhat.qe.model.MigrateBrickWrapper;
import com.redhat.qe.model.MigrateBrickWrapperList;

public class MigrateBrickJaxbContext {
	//singleton
	private static JAXBContext context;

	public static JAXBContext getContext() throws JAXBException {
		if (context == null) {
			context = JAXBContext.newInstance(MigrateBrickWrapper.class, MigrateBrickWrapperList.class);
		}
		return context;

	}

	public static Marshaller getMarshaller(){
		try {
			return getContext().createMarshaller();
		} catch (JAXBException e) {
			throw new RuntimeException(e);
		}
	}
	public static  String marshal(Object obj){
		try {
			return MyMarshaller.marshall(getContext(), obj);
		} catch (JAXBException e) {
			throw new RuntimeException("couldn't marshall obj", e);
		}
	}
	
	public static Unmarshaller getUnmarshaller(){
		try {
			return getContext().createUnmarshaller();
		} catch (JAXBException e) {
			throw new RuntimeException(e);
		}
	}
}
