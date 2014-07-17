package com.hbgz.pub.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
/**
 * 
 *@author ran haiquan 18907181648@189.cn
 *
 */
@Target(ElementType.METHOD)    
@Retention(RetentionPolicy.RUNTIME)    
@Documented   
@Inherited  
public @interface ServiceType {
	public String value();
}
