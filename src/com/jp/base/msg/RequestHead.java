package com.jp.base.msg;

import java.io.Serializable;

/**
 * @ClassName: RequestHeadMsg
 * @Description: 请求HEAD
 * @author: leven
 * @date: 2016年7月22日 上午9:47:12
 */

public class RequestHead implements Serializable {

	private static final long serialVersionUID = 1L;

	// 请求渠道来源 101,102,103,104
	private String appcode;

	// APP版本号
	private String appversion;

	// 系统信息
	private String ostype;

	// 操作系统版本
	private String osversion;

	// 手机品牌
	private String phonebrand;
	// 手机型号
	private String phonemodel;

	// 手机串号
	private String imei;

	// 浏览器名称
	private String browser;
	// 浏览器版本
	private String browserversion;
	// 扩展信息
	private String extinfo;
	// token
	private String token;

	private String ip;

	private String uionid;

	public String getUionid() {
		return uionid;
	}

	public void setUionid(String uionid) {
		this.uionid = uionid;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	/**
	 * 获取 去到来源的类型
	 * 
	 * @Title: getChannelID
	 * @Description: TODO
	 * @return
	 * @return: int
	 */
	public int getChannelID() {
		// ANDROID
		if (appcode.toLowerCase().equals("102"))
			return 1;
		// IOS
		if (appcode.toLowerCase().equals("103"))
			return 2;
		// MOT
		if (appcode.toLowerCase().equals("101"))
			return 3;
		// PHP
		if (appcode.toLowerCase().equals("104"))
			return 4;
		// ANDROID（大一类）
		if (appcode.toLowerCase().equals("10201"))
			return 5;
		// IOS（大一类）
		if (appcode.toLowerCase().equals("10301"))
			return 6;
		// MOT-ANDROID
		if (appcode.toLowerCase().equals("10202"))
			return 7;
		// MOT-IOS
		if (appcode.toLowerCase().equals("10302"))
			return 8;
		return 0;

	}

	public String getChannel() {
		// MOT
		if (appcode.toLowerCase().equals("101"))
			return "PC";
		// ANDROID
		if (appcode.toLowerCase().equals("102"))
			return "MOBILE";
		// IOS
		if (appcode.toLowerCase().equals("103"))
			return "MOBILE";
		// PHP
		if (appcode.toLowerCase().equals("104"))
			return "WEB";
		// ANDROID（大一类）
		if (appcode.toLowerCase().equals("10201"))
			return "MOBILE";
		// IOS（大一类）
		if (appcode.toLowerCase().equals("10301"))
			return "MOBILE";
		// MOT-ANDROID
		if (appcode.toLowerCase().equals("10202"))
			return "MOBILE";
		// MOT-IOS
		if (appcode.toLowerCase().equals("10302"))
			return "MOBILE";
		return "";

	}

	public String getAppcode() {
		return appcode;
	}

	public String getAppversion() {
		return appversion;
	}

	public String getBrowser() {
		return browser;
	}

	public String getBrowserversion() {
		return browserversion;
	}

	public String getExtinfo() {
		return extinfo;
	}

	public String getImei() {
		return imei;
	}

	public String getOstype() {
		return ostype;
	}

	public String getOsversion() {
		return osversion;
	}

	public String getPhonebrand() {
		return phonebrand;
	}

	public String getPhonemodel() {
		return phonemodel;
	}

	public String getToken() {
		return token;
	}

	public void setAppcode(String appcode) {
		this.appcode = appcode;
	}

	public void setAppversion(String appversion) {
		this.appversion = appversion;
	}

	public void setBrowser(String browser) {
		this.browser = browser;
	}

	public void setBrowserversion(String browserversion) {
		this.browserversion = browserversion;
	}

	public void setExtinfo(String extinfo) {
		this.extinfo = extinfo;
	}

	public void setImei(String imei) {
		this.imei = imei;
	}

	public void setOstype(String ostype) {
		this.ostype = ostype;
	}

	public void setOsversion(String osversion) {
		this.osversion = osversion;
	}

	public void setPhonebrand(String phonebrand) {
		this.phonebrand = phonebrand;
	}

	public void setPhonemodel(String phonemodel) {
		this.phonemodel = phonemodel;
	}

	public void setToken(String token) {
		this.token = token;
	}

	@Override
	public String toString() {
		return "RequestHead [appcode=" + appcode + ", appversion=" + appversion + ", ostype=" + ostype + ", osversion="
				+ osversion + ", phonebrand=" + phonebrand + ", phonemodel=" + phonemodel + ", imei=" + imei
				+ ", browser=" + browser + ", browserversion=" + browserversion + ", extinfo=" + extinfo + ", token="
				+ token + "]";
	}

}
