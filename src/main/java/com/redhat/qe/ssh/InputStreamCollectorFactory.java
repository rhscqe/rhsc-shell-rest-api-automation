package com.redhat.qe.ssh;

import java.io.InputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public interface InputStreamCollectorFactory<T extends InputStreamCollector> {
	
	public T create(InputStream stream);
//	public ReadInput getReadInput(Class<? extends ReadInput> clazz, InputStream inputStream) {
//		Constructor<? extends ReadInput> constructor = getConstructor(clazz);
//		return getInstance(inputStream, constructor); 
//	}
//	
//	public ReadInput getReadInput(Class<? extends ReadInput> clazz, InputStream inputStream, Duration duration) {
//		Constructor<? extends ReadInput> constructor = getConstructorWithDuration(clazz);
//		return getInstance(inputStream, duration, constructor); 
//	}
//
//	/**
//	 * @param inputStream
//	 * @param constructor
//	 * @return 
//	 */
//	private ReadInput getInstance(InputStream inputStream, Constructor<? extends ReadInput> constructor) {
//		ReadInput readInput = null;
//		try {
//			readInput = constructor.newInstance(inputStream);
//		} catch (InstantiationException e) {
//			throw new RuntimeException(e);
//		} catch (IllegalAccessException e) {
//			throw new RuntimeException(e);
//		} catch (IllegalArgumentException e) {
//			throw new RuntimeException(e);
//		} catch (InvocationTargetException e) {
//			throw new RuntimeException(e);
//		}
//		return readInput;
//	}
//	
//	private ReadInput getInstance(InputStream inputStream, Duration duration, Constructor<? extends ReadInput> constructor) {
//		ReadInput readInput = null;
//		try {
//			readInput = constructor.newInstance(inputStream, duration);
//		} catch (InstantiationException e) {
//			throw new RuntimeException(e);
//		} catch (IllegalAccessException e) {
//			throw new RuntimeException(e);
//		} catch (IllegalArgumentException e) {
//			throw new RuntimeException(e);
//		} catch (InvocationTargetException e) {
//			throw new RuntimeException(e);
//		}
//		return readInput;
//	}
//	/**
//	 * @param clazz
//	 * @return
//	 */
//	private Constructor<? extends ReadInput> getConstructor(Class<? extends ReadInput> clazz) {
//		Constructor<? extends ReadInput> constructor;
//		try {
//			constructor = clazz.getConstructor(InputStream.class);
//		} catch (NoSuchMethodException e) {
//			throw new RuntimeException(e);
//		} catch (SecurityException e) {
//			throw new RuntimeException(e);
//		}
//		return constructor;
//	}
//	
//	private Constructor<? extends ReadInput> getConstructorWithDuration(Class<? extends ReadInput> clazz) {
//		Constructor<? extends ReadInput> constructor;
//		try {
//			constructor = clazz.getConstructor(InputStream.class, Duration.class);
//		} catch (NoSuchMethodException e) {
//			throw new RuntimeException(e);
//		} catch (SecurityException e) {
//			throw new RuntimeException(e);
//		}
//		return constructor;
//	}

}
