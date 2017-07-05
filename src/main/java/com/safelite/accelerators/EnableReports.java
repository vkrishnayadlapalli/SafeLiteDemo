package com.safelite.accelerators;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE) 
public @interface EnableReports {
	
	String getTestCaseName() default "";
	
	String getBrowserType() default "";
	
	String getIpAddress() default "";
	
}
