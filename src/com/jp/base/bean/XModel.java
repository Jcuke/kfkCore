package com.jp.base.bean;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * 
 * ClassName: MyBatisModel
 * @Description: TODO
 * @author liw
 * @date 2015年9月9日
 * @version 1.0
 */

public class XModel implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5676530365482202628L;

	public static final String sortBy_ASC = "ASC";
	public static final String sortBy_DESC = "DESC";


	@JsonIgnore
	protected String orderBy;
	
	
	@JsonIgnore
	protected String beginRow;

	@JsonIgnore
	protected String sortBy;

	@JsonIgnore
	protected Integer pageIndex;

	@JsonIgnore
	protected Integer pageSize;

	public String getOrderBy() {
		return orderBy;
	}

	public void setOrderBy(String orderBy) {
		this.orderBy = orderBy;
	}

	public String getSortBy() {
		return sortBy;
	}

	public void setSortBy(String sortBy) {
		this.sortBy = sortBy;
	}

	public Integer getBeginRow() {
		if (pageIndex != null) {
			return (pageIndex - 1) * pageSize;
		}
		return pageIndex;
	}

	public void setPageIndex(Integer pageIndex) {
		this.pageIndex = pageIndex;
	}

	public Integer getPageIndex() {
		return pageIndex;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

}
