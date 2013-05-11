package com.railinc.totoro.responsibility;

import com.railinc.totoro.domain.ResponsiblePersonType;
import com.railinc.totoro.domain.SourceSystem;
import com.railinc.totoro.util.CriteriaWithPaging;

public class ResponsibilityCriteria extends CriteriaWithPaging {

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
