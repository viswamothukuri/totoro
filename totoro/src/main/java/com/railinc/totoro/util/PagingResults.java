package com.railinc.totoro.util;

import static com.google.common.collect.Lists.newArrayList;

import java.util.Collection;

public class PagingResults {
	private final PagingParameters parameters = new PagingParameters();
	private int totalCount = 0;

	public PagingResults() {}
	
	public PagingResults(CriteriaWithPaging criteria, int count) {
		parameters.setPage(criteria.getPage());
		parameters.setPageSize(criteria.getPageSize());
		this.totalCount = count;
	}

	public Collection<Integer> getPages() {
		Collection<Integer> c = newArrayList();
		for (int i=0;i<getPageCount();i++) {
			c.add(i);
		}
		return c;		
	}
	
	public int getPageCount() {
		if (parameters.getPageSize() == 1) {
			return (int) totalCount;
		}
		return (totalCount / getPageSize()) + (totalCount % getPageSize()) > 0 ? 1 : 0;
	}

	public int getTotalCount() {
		return totalCount;
	}
	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}


	public int getPage() {
		return parameters.getPage();
	}

	public void setPage(int value) {
		parameters.setPage(value);
	}

	public int getPageSize() {
		return parameters.getPageSize();
	}

	public void setPageSize(int v) {
		parameters.setPageSize(v);
	}

	public int getMaxPageSize() {
		return parameters.getMaxPageSize();
	}

	public void setMaxPageSize(int v) {
		parameters.setMaxPageSize(v);
	}

	public void set(PagingResults paging) {
		setPage(paging.getPage());
		setPageSize(paging.getPageSize());
		setTotalCount(paging.getTotalCount());
	}

}
