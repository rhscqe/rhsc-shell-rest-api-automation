package com.redhat.qe.ssh;

import java.io.InputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public class ReadInputFactory {

	public ReadInput getReadInput(Class<? extends ReadInput> clazz, InputStream inputStream) {
		Constructor<? extends ReadInput> constructor = getConstructor(clazz);
		return getInstance(inputStream, constructor); 
	}

	/**
	 * @param inputStream
	 * @param constructor
	 * @return 
	 */
	private ReadInput getInstance(InputStream inputStream, Constructor<? extends ReadInput> constructor) {
		ReadInput readInput = null;
		try {
			readInput = constructor.newInstance(inputStream);
		} catch (InstantiationException e) {
			throw new RuntimeException(e);
		} catch (IllegalAccessException e) {
			throw new RuntimeException(e);
		} catch (IllegalArgumentException e) {
			throw new RuntimeException(e);
		} catch (InvocationTargetException e) {
			throw new RuntimeException(e);
		}
		return readInput;
	}

	/**
	 * @param clazz
	 * @return
	 */
	private Constructor<? extends ReadInput> getConstructor(Class<? extends ReadInput> clazz) {
		Constructor<? extends ReadInput> constructor;
		try {
			constructor = clazz.getConstructor(InputStream.class);
		} catch (NoSuchMethodException e) {
			throw new RuntimeException(e);
		} catch (SecurityException e) {
			throw new RuntimeException(e);
		}
		return constructor;
	}

}
