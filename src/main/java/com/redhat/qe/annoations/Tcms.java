package com.redhat.qe.annoations;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface Tcms {
	String[] value() ;
}
