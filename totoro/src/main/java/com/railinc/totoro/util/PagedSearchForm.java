package com.railinc.totoro.util;

import java.io.Serializable;

public abstract class PagedSearchForm<C extends CriteriaWithPaging,R> implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4180264693103486439L;
	
	
	private final PagedCollection<R> results = new PagedCollection<R>();

	public PagedSearchForm() {
		
	}
	
	public PagingResults getPaging() {
		return results.getPaging();
	}
	
	public abstract C getCriteria();

	public void setResults(PagedCollection<R> r) {
		results.set(r);
	}
	
	public PagedCollection<R> getResults() {
		return results;
	}
	

	

}
