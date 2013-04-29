package com.railinc.totoro.responsibility;

import java.util.Collection;

import com.google.common.collect.Range;
import com.railinc.totoro.domain.ResponsiblePersonType;
import com.railinc.totoro.domain.SourceSystem;

public class ResponsibilityCriteria {
	int page = 0;
	int pageSize = 20;
	
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

	private SourceSystem sourceSystem;
    private Long ruleNumber;
	private ResponsiblePersonType personType;
	private String person;
	public SourceSystem getSourceSystem() {
		return sourceSystem;
	}
	public void setSourceSystem(SourceSystem sourceSystem) {
		this.sourceSystem = sourceSystem;
	}
	public Long getRuleNumber() {
		return ruleNumber;
	}
	public void setRuleNumber(Long ruleNumber) {
		this.ruleNumber = ruleNumber;
	}
	public ResponsiblePersonType getPersonType() {
		return personType;
	}
	public void setPersonType(ResponsiblePersonType personType) {
		this.personType = personType;
	}
	public String getPerson() {
		return person;
	}
	public void setPerson(String person) {
		this.person = person;
	}

	
}
