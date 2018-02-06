package com.jp.base.tool.doc;

import java.util.List;

public class ArgInfo {
	boolean primitive;
	boolean map;
	Class<?> type;
	List<FieldInfo> argFields;

	public boolean isPrimitive() {
		return primitive;
	}

	public void setPrimitive(boolean primitive) {
		this.primitive = primitive;
	}

	public List<FieldInfo> getArgFields() {
		return argFields;
	}

	public void setArgFields(List<FieldInfo> argFields) {
		this.argFields = argFields;
	}

	public Class<?> getType() {
		return type;
	}

	public void setType(Class<?> type) {
		this.type = type;
	}

	public boolean isMap() {
		return map;
	}

	public void setMap(boolean map) {
		this.map = map;
	}

}
