package com.railinc.totoro.util;

import java.io.Serializable;

public abstract class PagedSearchForm<C extends CriteriaWithPaging,R> implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4180264693103486439L;
	
	private final C criteria;
	private final PagedCollection<R> results = new PagedCollection<R>();

	public PagedSearchForm() {
		criteria = newCriteria();
	}
	
	protected abstract C newCriteria();

	public PagingResults getPaging() {
		return results.getPaging();
	}
	
	public C getCriteria() {
		return criteria;
	}

	public void setResults(PagedCollection<R> r) {
		results.set(r);
	}
	
	public PagedCollection<R> getResults() {
		return results;
	}
	

	

}
