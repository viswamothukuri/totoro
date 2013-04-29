package com.railinc.totoro.responsibility;

import static org.apache.commons.lang.StringUtils.isBlank;
import static org.apache.commons.lang.StringUtils.isNotBlank;
import static org.apache.commons.lang.StringUtils.isNumeric;

import java.io.Serializable;

import com.railinc.totoro.util.PagedCollection;
import com.railinc.totoro.util.Paging;

public class ResponsibilitySearchForm implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7112438745586765432L;
	
	private ResponsibilityCriteria criteria = new ResponsibilityCriteria();
	private final PagedCollection<ResponsibilityForm> results = new PagedCollection<ResponsibilityForm>();



	public Paging getPaging() {
		return results.getPaging();
	}
	
	public ResponsibilityCriteria getCriteria() {
		return criteria;
	}

	public void setCriteria(ResponsibilityCriteria criteria) {
		this.criteria = criteria;
	}

	public void setResults(PagedCollection<ResponsibilityForm> r) {
		results.clear();
		results.addAll(r);
		results.getPaging().set(r.getPaging());
	}
	
	public PagedCollection<ResponsibilityForm> getResults() {
		return results;
	}
	
	public void setQuery(String text) {
		if (isBlank(text)) {return;}
		if (isNumeric(text)) {
			criteria.setRuleNumber(Long.valueOf(text));
		} else {
			criteria.setPerson(text);
		}
	}
	public String getQuery() {
		if (isNotBlank(criteria.getPerson())) {
			return criteria.getPerson();
		} else if (criteria.getRuleNumber() != null) {
			return String.valueOf(criteria.getRuleNumber());
		} else {
			return "";
		}
	}

	

}
