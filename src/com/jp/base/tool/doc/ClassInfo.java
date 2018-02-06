package com.jp.base.tool.doc;

import java.util.List;

public class ClassInfo implements Comparable {
	Class c;
	List<MethodInfo> methods;

	public int compareTo(Object o) {
		if (o instanceof ClassInfo) {
			ClassInfo other = (ClassInfo) o;
			return c.getSimpleName().compareTo(other.c.getSimpleName());
		} else {
			return -1;
		}
	}

	public List<MethodInfo> getMethods() {
		return methods;
	}

	public void setMethods(List<MethodInfo> methods) {
		this.methods = methods;
	}

	public Class getC() {
		return c;
	}

	public void setC(Class c) {
		this.c = c;
	}

}
