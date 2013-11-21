package com.redhat.qe.repository.rest.context;

import java.io.StringReader;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import com.redhat.qe.helpers.jaxb.MyMarshaller;
import com.redhat.qe.model.jaxb.MigrateBrickAction;
import com.redhat.qe.model.jaxb.MigrateBrickWrapper;
import com.redhat.qe.model.jaxb.MigrateBrickWrapperList;
import com.redhat.qe.model.jaxb.MigrateBrickWrapperList2;

public class MigrateBrickJaxbContext {
	//singleton
	private static JAXBContext context;

	public static JAXBContext getContext() throws JAXBException {
		if (context == null) {
			context = JAXBContext.newInstance(MigrateBrickAction.class, MigrateBrickWrapper.class, MigrateBrickWrapperList.class, MigrateBrickWrapperList2.class);

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

	public static Object unmarshal(String body) {
		try {
			return getContext().createUnmarshaller().unmarshal(new StringReader(body));
		} catch (JAXBException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
}
