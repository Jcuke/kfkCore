package com.jp.base.target;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @ClassName: MethodTarg
 * @Description: 方法泛型
 * @author: leven
 * @date: 2016年8月8日 上午9:13:58
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Documented
public @interface MethodTarg {
	
	/**
	 * 
	 * @Title: describe
	 * @Description: 描述
	 * @return
	 * @return: String
	 */
	public String describe() default "";

	/**
	 * @Title: RESPClzz
	 * @Description: 返回泛型
	 * @return
	 * @return: Class<?>
	 */
	public Class<?> RESPClzz();

	/**
	 * @Title: REQClzz
	 * @Description: 请求泛型
	 * @return
	 * @return: Class<?>
	 */
	public Class<?> REQClzz();

}
