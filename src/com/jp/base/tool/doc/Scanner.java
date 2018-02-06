package com.jp.base.tool.doc;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.type.classreading.CachingMetadataReaderFactory;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.core.type.classreading.MetadataReaderFactory;

import com.jp.base.target.MethodTarg;

public class Scanner {

	public static List<ClassInfo> scan(String config) throws Exception {
		return scan(config, "");
	}

	public static List<ClassInfo> scan(String config, String exclude) throws Exception {
		PathMatchingResourcePatternResolver resourcePatternResolver = new PathMatchingResourcePatternResolver();
		MetadataReaderFactory metadataReaderFactory = new CachingMetadataReaderFactory(resourcePatternResolver);
		Resource[] resources = resourcePatternResolver.getResources(config);
		String[] excludes=null;
		if(exclude!=null)
			excludes=exclude.split(",");
		else
			excludes=new String[0];
		List cList = new ArrayList();

		for (Resource e : resources) {
			MetadataReader metadataReader = metadataReaderFactory.getMetadataReader(e);
			if (metadataReader.getClassMetadata().isInterface()) {
				if (metadataReader.getClassMetadata().getClassName().startsWith("com.ysl.core"))
					continue;
				String name=metadataReader.getClassMetadata().getClassName();
				if(contains(excludes,name))
					continue;
				ClassInfo c = new ClassInfo();
				
				c.c = Class.forName(name);

				cList.add(c);
			}
		}
		Collections.sort(cList);
		return cList;
	}
	static boolean	contains(String[] excludes,String name){
		for(int i=0;i<excludes.length;i++){
			if(excludes[i]==null||excludes[i].length()<1)
				continue;
			/*
			if(name.endsWith(excludes[i]))
				return true;
				*/
			if(name.indexOf(excludes[i])>=0)
				return true;
		}
		return false;
	}
	public static void scanClass(ClassInfo c) throws Exception {
		c.methods = scanClass(c.c);
		for (int i = 0; i < c.methods.size(); i++) {
			scanMethod(c.methods.get(i));
		}
	}

	public static List<MethodInfo> scanClass(Class<?> c) throws Exception {
		List<MethodInfo> list = new ArrayList<MethodInfo>();
		Method[] methods = c.getDeclaredMethods();
		for (int i = 0; i < methods.length; i++) {
			/*
			MethodInfo m = new MethodInfo();
			m.name = methods[i].getName();
			m.returnType = methods[i].getReturnType();
			if(methods[i].getParameterTypes()!=null&&methods[i].getParameterTypes().length==1){
				m.argType = methods[i].getParameterTypes()[0];
				list.add(m);
			}
			*/
			list.add(getMethodInfo(methods[i]));
		}
		Collections.sort(list);
		return list;
	}
	
	public	static MethodInfo getMethodInfo(Method method) throws Exception{
		MethodInfo m = new MethodInfo();
		m.name = method.getName();
        String describe = method.getAnnotation(MethodTarg.class).describe();
        m.setAnnotationDescribe(describe);
		ArgInfo returnInfo=new ArgInfo();
		returnInfo.type= method.getReturnType();
		m.setReturnInfo(returnInfo);
		//m.returnType = method.getReturnType();
		Class<?>[] types=method.getParameterTypes();
		List<ArgInfo> args=new ArrayList<ArgInfo>();
		for(int i=0;i<types.length;i++){
			ArgInfo arg=new ArgInfo();
			Class<?> type=types[i];
			arg.setType(type);
			args.add(arg);
		}
		m.setArgs(args);
		return m;
	}

	public static void scanMethod(MethodInfo mi) throws Exception {
		scanArg(mi.returnInfo);
		//mi.argFields = scanFields(mi.argType);
		List<ArgInfo> args=mi.getArgs();
		for(int i=0;i<args.size();i++){
			
			ArgInfo arg=args.get(i);
			/*
			Class<?> type=arg.getType();
			if(isPrimitive(type)){
				arg.setPrimitive(true);
			}else if(isMap(type)){
				arg.setMap(true);
			}else{
				arg.setArgFields(scanFields(type));
			}*/
			scanArg(arg);
		}
	}
	static void scanArg(ArgInfo arg)throws Exception{
		Class<?> type=arg.getType();
		if(isPrimitive(type)){
			arg.setPrimitive(true);
		}else if(isMap(type)){
			arg.setMap(true);
		}else{
			arg.setArgFields(scanFields(type));
		}
		
	}

	public static List<FieldInfo> scanFields(Class c) throws Exception {
		
		BeanInfo info = null;
		try{
		info=Introspector.getBeanInfo(c);
		}catch(Exception e){
			e.printStackTrace();
			throw e;
		}
		PropertyDescriptor[] props = info.getPropertyDescriptors();

		List list = new ArrayList();
		for (int i = 0; i < props.length; i++) {
			PropertyDescriptor prop = props[i];
			if ("class".equals(prop.getName()))
				continue;
			FieldInfo fi = new FieldInfo();
			list.add(fi);
			Method accessor = prop.getReadMethod();
			Class rClass = accessor.getReturnType();
			Type rType = accessor.getGenericReturnType();

			fi.name = prop.getName();
			fi.type = rClass.getName();

			/*
			 * if(rClass.isArray()){
			 * 
			 * }
			 */
			if (isPrimitive(rClass)) {
				fi.isprim = true;
			} else if (isList(rClass)) {
				if (rType instanceof ParameterizedType) {
					ParameterizedType pt = (ParameterizedType) rType;
					Type subType = pt.getActualTypeArguments()[0];
					if (subType instanceof Class) {
						Class subC = (Class) subType;
						List<FieldInfo> subFields = scanFields(subC);
						fi.isarray = true;
						fi.subFields = subFields;
					}
				}
			} else {
				List<FieldInfo> subFields = scanFields(rClass);
				fi.subFields = subFields;
			}
		}
		return list;
	}
	public static boolean isMap(Class c){
		String name = c.getName();
		
		return name.indexOf("Map")>0;
	}
	public static boolean isPrimitive(Class c) {
		if (c.isPrimitive())
			return true;
		String name = c.getName();
		if (name.startsWith("java.lang."))
			return true;
		if (c.equals(java.util.Date.class))
			return true;
		if (c.equals(java.sql.Date.class)) {
			return true;
		}
		if (c.equals(BigDecimal.class)) {

			return true;
		} else if (c.equals(BigInteger.class)) {
			return true;
		}
		return false;
	}

	public static boolean isList(Class c) {
		if ("java.util.List".equals(c.getName()))
			return true;
		Class[] is = c.getInterfaces();
		for (int i = 0; i < is.length; i++) {
			if ("java.util.List".equals(is[i].getName()))
				return true;
		}
		return false;
	}
}
