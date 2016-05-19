package com.virtyx.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ValidateJson {
	
	@SuppressWarnings("rawtypes")
	Class<? extends ValidateJsonDefault> validate() default ValidateJsonDefault.class;

}
