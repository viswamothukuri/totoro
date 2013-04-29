package com.railinc.totoro.util;

import java.util.ArrayList;
import java.util.Collection;

public class PagedCollection<T> extends ArrayList<T> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5341450194060025414L;

	private final Paging paging = new Paging();

	public PagedCollection() {
	}

	public PagedCollection(Collection<T> all) {
		addAll(all);
	}

	public PagedCollection(Collection<T> all, Paging paging) {
		this(all);
		this.paging.set(paging);
	}

	public Paging getPaging() {
		return paging;
	}

}
