package com.jp.base.msg;

import java.io.Serializable;

import com.jp.base.excep.ApiException;

/**
 * @ClassName: RequestBodyMsg
 * @Description: 请求BODY
 * @author: leven
 * @date: 2016年7月22日 上午9:49:10
 */
public abstract class RequestBody implements Serializable {

	/**
	 * @fieldName: serialVersionUID
	 * @fieldType: long
	 * @Description: TODO
	 */
	private static final long serialVersionUID = 1L;
	
	//参数校验
	public abstract void check() throws ApiException;
}
