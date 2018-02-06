package com.jp.base.msg;

import com.fasterxml.jackson.annotation.JsonProperty;


/**
 * @ClassName: RequestBaseMsg
 * @Description: 请求基类
 * @author: leven
 * @date: 2016年7月22日 上午10:10:09
 * @param <T>
 */
public class RequestBase<T extends RequestBody> {
	
	@JsonProperty(value="h")
	private RequestHead head;

	@JsonProperty(value="p")
	private T body;

	public RequestHead getHead() {
		return head;
	}

	public void setHead(RequestHead head) {
		this.head = head;
	}

	public T getBody() {
		return body;
	}

	public void setBody(T body) {
		this.body = body;
	}

}
