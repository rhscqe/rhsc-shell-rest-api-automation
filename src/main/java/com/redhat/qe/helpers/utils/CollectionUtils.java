package com.redhat.qe.helpers.utils;

import java.util.Collection;

import junit.framework.Assert;

import com.google.common.base.Predicate;
import com.google.common.collect.Collections2;

public class CollectionUtils {
	
	public static <T> T findFirst(Collection<T> collection, Predicate<T> predicate){
		T result = _findFirst(collection, predicate);
		Assert.assertNotNull("could not find element",result);
		return result;
	}
	public static <T> T _findFirst(Collection<T> collection, Predicate<T> predicate){
		Collection<T> result = Collections2.filter(collection, predicate);
		if( result.size() > 0 ){
			return result.iterator().next();
		}else{
			return null;
		}
	}

}
