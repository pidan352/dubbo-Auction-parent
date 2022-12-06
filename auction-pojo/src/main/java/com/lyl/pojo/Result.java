package com.lyl.pojo;

import com.github.pagehelper.PageInfo;

import java.io.Serializable;
import java.util.List;

/**
 * 功能：分页数据封装类
 *
 * @author 林亦亮
 * @version 1.0
 * @date 2022/12/6
 */

public class Result implements Serializable {
	private List data;
	private PageInfo pageInfo;

	public List getData() {
		return data;
	}

	public void setData(List data) {
		this.data = data;
	}

	public PageInfo getPageInfo() {
		return pageInfo;
	}

	public void setPageInfo(PageInfo pageInfo) {
		this.pageInfo = pageInfo;
	}
}
