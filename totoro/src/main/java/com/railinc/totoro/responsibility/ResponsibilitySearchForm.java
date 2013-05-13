package com.railinc.totoro.responsibility;

import static org.apache.commons.lang.StringUtils.isNotBlank;
import static org.apache.commons.lang.StringUtils.isNumeric;

import org.apache.commons.lang.StringUtils;

import com.railinc.totoro.domain.IdentityType;
import com.railinc.totoro.domain.SourceSystem;
import com.railinc.totoro.util.PagedSearchForm;

public class ResponsibilitySearchForm extends PagedSearchForm<ResponsibilityCriteria, ResponsibilityForm> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7112438745586765432L;
	
	private String query;
	private SourceSystem sourceSystem;
	private IdentityType personType;
	

	
	public SourceSystem getSourceSystem() {
		return sourceSystem;
	}

	public void setSourceSystem(SourceSystem sourceSystem) {
		this.sourceSystem = sourceSystem;
	}

	public IdentityType getPersonType() {
		return personType;
	}

	public void setPersonType(IdentityType personType) {
		this.personType = personType;
	}

	@Override
	public ResponsibilityCriteria getCriteria() {
		ResponsibilityCriteria criteria = new ResponsibilityCriteria();
		criteria.setSourceSystem(sourceSystem);
		criteria.setPersonType(personType);
		criteria.setFreeText(query);
		return criteria;
	}
	
	public String getQuery() {
		return query;
	}
	public void setQuery(String in) {
		this.query = StringUtils.trimToNull(in);
	}


	

}
