package com.jp.base.tool.doc;

import java.util.List;

public 	class	FieldInfo implements Comparable{
	String	name;
	String	type;
	boolean isprim;
	boolean isarray;
	List	subFields;
	
	public int compareTo(Object o){
		if(o instanceof FieldInfo){
			FieldInfo mi=(FieldInfo)o;
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

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public boolean isIsprim() {
		return isprim;
	}

	public void setIsprim(boolean isprim) {
		this.isprim = isprim;
	}

	public boolean isIsarray() {
		return isarray;
	}

	public void setIsarray(boolean isarray) {
		this.isarray = isarray;
	}

	public List getSubFields() {
		return subFields;
	}

	public void setSubFields(List subFields) {
		this.subFields = subFields;
	}
	
}