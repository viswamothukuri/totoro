package com.railinc.totoro.util;

import static com.google.common.collect.Lists.newArrayList;

import java.util.Collection;

public class Paging {
	private int page = 0;
	private int pageSize = 20;
	private int totalCount = 0;

	public Paging() {}
	
	public Collection<Integer> getPages() {
		Collection<Integer> c = newArrayList();
		for (int i=0;i<getPageCount();i++) {
			c.add(i);
		}
		return c;		
	}
	public int getPageCount() {
		if (pageSize==1) {
			return totalCount;
		}
		return (totalCount / pageSize) + (totalCount % pageSize) > 0 ? 1 : 0;
	}

	public int getTotalCount() {
		return totalCount;
	}
	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}
	public int getPage() {
		return page;
	}
	public void setPage(int page) {
		this.page = page;
	}
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public void set(Paging paging) {
		setPage(paging.getPage());
		setPageSize(paging.getPageSize());
		setTotalCount(paging.getTotalCount());
		
		
	}
}
