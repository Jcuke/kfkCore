package com.jp.base.tool.doc;

import java.util.List;

public class MethodInfo implements Comparable{
	String	name;
	List<ArgInfo> args;
	ArgInfo	returnInfo;
	
	Class<?>	returnType;
	List<FieldInfo>	returnFields;
	Class<?>	argType;
	List<FieldInfo>	argFields;

    String annotationDescribe;
    String interfaceSimpleName;


	
	public int compareTo(Object o){
		if(o instanceof MethodInfo){
			MethodInfo mi=(MethodInfo)o;
			return name.compareTo(mi.name);
		}else{
			return -1;
		}
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Class<?> getReturnType() {
		return returnType;
	}
	public void setReturnType(Class<?> returnType) {
		this.returnType = returnType;
	}
	
	
	public List<ArgInfo> getArgs() {
		return args;
	}
	public void setArgs(List<ArgInfo> args) {
		this.args = args;
	}
	public List<FieldInfo> getReturnFields() {
		return returnFields;
	}
	public void setReturnFields(List<FieldInfo> returnFields) {
		this.returnFields = returnFields;
	}
	public Class<?> getArgType() {
		return argType;
	}
	public void setArgType(Class<?> argType) {
		this.argType = argType;
	}
	public List<FieldInfo> getArgFields() {
		return argFields;
	}
	public void setArgFields(List<FieldInfo> argFields) {
		this.argFields = argFields;
	}
	public ArgInfo getReturnInfo() {
		return returnInfo;
	}
	public void setReturnInfo(ArgInfo returnInfo) {
		this.returnInfo = returnInfo;
	}

    public String getAnnotationDescribe() {
        return annotationDescribe;
    }

    public void setAnnotationDescribe(String annotationDescribe) {
        this.annotationDescribe = annotationDescribe;
    }

    public String getInterfaceSimpleName() {
        return interfaceSimpleName;
    }

    public void setInterfaceSimpleName(String interfaceSimpleName) {
        this.interfaceSimpleName = interfaceSimpleName;
    }
}
