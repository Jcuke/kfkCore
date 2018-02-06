package com.jp.base.msg;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.jp.base.tool.config.SYSConfig;

/**
 * 
 * @ClassName: 返回基类
 * @Description: TODO
 * @author: leven
 * @date: 2016年8月3日 下午3:31:53
 * @param <T>
 */
public class ResponseBase<T> implements java.io.Serializable{

	private String c;
	
	private String m;
	
	private String v = SYSConfig.coreVersion;
	
	private String e;
	
	@JsonProperty(value="p")
	private T body;

	public ResponseBase() {
		super();
	}

	public ResponseBase(T body) {
		super();
		this.body = body;
	}

	public String getC() {
		return c;
	}

	public void setC(String c) {
		this.c = c;
	}

	public String getM() {
		return m;
	}

	public void setM(String m) {
		this.m = m;
	}

	public T getBody() {
		return body;
	}

	public void setBody(T body) {
		this.body = body;
	}

	public void setV(String v) {
		this.v = v;
	}

	public String getV() {
		return v;
	}

	public String getE() {
		return e;
	}

	public void setE(String e) {
		this.e = e;
	}

}
