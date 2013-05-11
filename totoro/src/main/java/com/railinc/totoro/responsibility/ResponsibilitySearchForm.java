package com.railinc.totoro.responsibility;

import static org.apache.commons.lang.StringUtils.isBlank;
import static org.apache.commons.lang.StringUtils.isNotBlank;
import static org.apache.commons.lang.StringUtils.isNumeric;

import com.railinc.totoro.util.PagedSearchForm;

public class ResponsibilitySearchForm extends PagedSearchForm<ResponsibilityCriteria, ResponsibilityForm> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7112438745586765432L;
	

	public void setQuery(String text) {
		if (isBlank(text)) {return;}
		if (isNumeric(text)) {
			getCriteria().setRuleNumber(Long.valueOf(text));
		} else {
			getCriteria().setPerson(text);
		}
	}
	public String getQuery() {
		if (isNotBlank(getCriteria().getPerson())) {
			return getCriteria().getPerson();
		} else if (getCriteria().getRuleNumber() != null) {
			return String.valueOf(getCriteria().getRuleNumber());
		} else {
			return "";
		}
	}

	@Override
	protected ResponsibilityCriteria newCriteria() {
		return new ResponsibilityCriteria();
	}

	

}
