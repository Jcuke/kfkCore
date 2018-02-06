package com.jp.base.excep;

/**
 * 
 * @ClassName: ApiException
 * @Description: 自定义异常
 * @author: liwei
 * @date: 2016年5月17日 上午10:24:03
 */
public class ApiException extends Exception {

	private static final long serialVersionUID = -128473878805641943L;

	private String errCode;
	private String errMsg;
	private boolean isNeedCatch;

	public ApiException() {
		super();
	}

	public ApiException(String message, Throwable cause) {
		super(message, cause);
	}

	public ApiException(String message) {
		super(message);
	}

	public ApiException(Throwable cause) {
		super(cause);
	}

	public ApiException(String errCode, String errMsg) {
		super(errCode + ":" + errMsg);
		this.errCode = errCode;
		this.errMsg = errMsg;
		this.isNeedCatch = false;
	}
	
	public ApiException(String errCode, String errMsg, boolean isNeedCatch) {
		super(errCode + ":" + errMsg);
		this.errCode = errCode;
		this.errMsg = errMsg;
		this.isNeedCatch = isNeedCatch;
	}

	public String getErrCode() {
		return this.errCode;
	}

	public String getErrMsg() {
		return this.errMsg;
	}

	public boolean isNeedCatch() {
		return isNeedCatch;
	}

	public void setNeedCatch(boolean isNeedCatch) {
		this.isNeedCatch = isNeedCatch;
	}
}
