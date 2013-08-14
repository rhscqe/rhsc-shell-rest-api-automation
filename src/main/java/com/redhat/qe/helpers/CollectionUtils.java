package com.redhat.qe.helpers;

import java.util.Collection;

import junit.framework.Assert;

import com.google.common.base.Predicate;
import com.google.common.collect.Collections2;

public class CollectionUtils {
	
	public static <T> T findFirst(Collection<T> collection, Predicate<T> predicate){
		Collection<T> result = Collections2.filter(collection, predicate);
		Assert.assertTrue("could not find element",result.size() > 0 );
		return result.iterator().next();
	}

}
