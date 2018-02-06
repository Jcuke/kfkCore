package com.jp.base.init;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.InitializingBean;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.jp.base.bean.MethodBean;
import com.jp.base.target.MethodTarg;
import com.jp.mmlbk.tool.utils.ClassUtil;

/**
 * 
 * @ClassName: TXManager
 * @Description: 反射工具类，扫描所有的指定报下的service及其方法
 * @date: 2016年8月3日 下午4:15:36
 */
public class TXManager implements InitializingBean {
	private static Logger logger = Logger.getLogger(TXManager.class);

	private static TXManager global = null;

	/**
	 * 定义classMap
	 */
	private Map<String, MethodBean> classMap = new HashMap<>();

	/**
	 * 定义代码扫描路径
	 */
	private static final String CLASSPATH = "com.jp.subject.mmlbk.channel";

	public static TXManager getGlobal() {
		return global;
	}

	public void afterPropertiesSet() throws Exception {
		global = this;
		this.init();
	}

	public Map<String, MethodBean> getClassMap() {
		return classMap;
	}

	public void setClassMap(Map<String, MethodBean> classMap) {
		this.classMap = classMap;
	}

	public MethodBean getMethodBean(String methodName) {
		return classMap.get(methodName);
	}

	public void init() {
		load();
	}

	/**
	 * @Title: load
	 * @Description: 初始化，扫描路径下所有的接口类，并将方法放入methodBean中，再放入classMap
	 * @return: void
	 */
	@SuppressWarnings({ "rawtypes" })
	void load() {
		Set<String> allClasses = new HashSet<String>();

		// 自定义扫描class过滤规则 ,入股需哟包含关键字，则传入关键字，否则传入空字符串为不过滤
		List<String> classFilters = new ArrayList<String>();
		classFilters.add("");

		// 创建一个扫描处理器，排除内部类 扫描符合条件的类
		ClassUtil handler = new ClassUtil(true, true, classFilters);

		Set<Class<?>> calssList = handler.getPackageAllClasses(CLASSPATH, true);
		// 将classname放入list中
		for (Class<?> cla : calssList) {
			// System.out.println(cla.getName());
			allClasses.add(cla.getName());
		}
		// 循环迭代list，将list中的interface取出并将方法遍历出来
		for (String className : allClasses) {
			try {
				Class clazz = Class.forName(className);
				if (clazz.isInterface()) {

					Method[] methods = clazz.getMethods();
					for (Method method : methods) {
						// 获取方法注解
						MethodTarg atTarg = method.getAnnotation(MethodTarg.class);
						if (atTarg != null) {
							// 请求参数泛型
							String reqActualType = atTarg.REQClzz().getName();
							// 返回参数泛型
							String respActualType = atTarg.RESPClzz().getName();

							Class<?>[] parameterTypes = method.getParameterTypes();

							String parameterName = "";
							String methodName = "";
							String txid = "";
							for (Class<?> clas : parameterTypes) {
								methodName = method.getName();
								parameterName = clas.getName(); // 参数类型

								MethodBean methodBean = new MethodBean();
								methodBean.setClassName(className); // 类路径
								methodBean.setParamClassName(parameterName); // 参数类型
								methodBean.setReqActualType(reqActualType); // 泛型
								methodBean.setRespActualType(respActualType);
								methodBean.setMethodName(methodName);

								txid = className + "-" + methodName;
								classMap.put(txid, methodBean);
//								logger.info(txid + "," + methodBean.toString());
							}

						}

					}
				}
			} catch (Exception e) {
				logger.error("Init error map failed", e);
				continue;
			}
		}
	}

	@SuppressWarnings("rawtypes")
	public static void main(String[] args) {
		Map<String, MethodBean> classMap = new HashMap<>();
		Set<String> allClasses = new HashSet<>();

		// 自定义过滤规则
		List<String> classFilters = new ArrayList<>();
		classFilters.add("");

		// 创建一个扫描处理器，排除内部类 扫描符合条件的类
		ClassUtil handler = new ClassUtil(true, true, classFilters);

		Set<Class<?>> calssList = handler.getPackageAllClasses("com.jp.subject.mmlbk.channel", true);
		for (Class<?> cla : calssList) {
			// System.out.println(cla.getName());
			allClasses.add(cla.getName());
		}
		for (String className : allClasses) {
			try {
				Class clazz = Class.forName(className);
				if (clazz.isInterface()) {

					Method[] methods = clazz.getMethods();
					for (Method method : methods) {
						// 获取方法注解
						MethodTarg atTarg = method.getAnnotation(MethodTarg.class);
						if (atTarg != null) {
							// 请求参数泛型
							String reqActualType = atTarg.REQClzz().getName();
							// 返回参数泛型
							String respActualType = atTarg.RESPClzz().getName();

							Class<?>[] parameterTypes = method.getParameterTypes();

							String parameterName = "";
							String methodName = "";
							String txid = "";
							for (Class<?> clas : parameterTypes) {
								methodName = method.getName();
								parameterName = clas.getName(); // 参数类型

								MethodBean methodBean = new MethodBean();
								methodBean.setClassName(className); // 类路径
								methodBean.setParamClassName(parameterName); // 参数类型
								methodBean.setReqActualType(reqActualType); // 泛型
								methodBean.setRespActualType(respActualType);
								methodBean.setMethodName(methodName);

								txid = className + "." + methodName;
								classMap.put(txid, methodBean);
								logger.info(txid + "," + methodBean.toString());
							}

						}

					}
				}
			} catch (Exception e) {
				logger.error("Init error map failed", e);
				continue;
			}
		}
		System.out.println(classMap.toString());

	}

}
