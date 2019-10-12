package io.renren.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.concurrent.TimeUnit;

/**
 * redis锁注解
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface RedisLock {

	String lockPrefix() default "";
	
	String lockKey() default "";
	
	long timeOut() default 5;
	
	TimeUnit timeUnit() default TimeUnit.SECONDS;
	 
}
