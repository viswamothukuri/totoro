package com.railinc.totoro.responsibility;

import org.apache.commons.lang.StringUtils;

import com.railinc.totoro.domain.IdentityType;
import com.railinc.totoro.domain.Responsibility;
import com.railinc.totoro.domain.RuleNumberType;
import com.railinc.totoro.domain.SourceSystem;
import com.railinc.totoro.util.CriteriaValue;
import com.railinc.totoro.util.CriteriaWithPaging;

public class ResponsibilityCriteria extends CriteriaWithPaging {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5043736016260052092L;
	
	
	private CriteriaValue<SourceSystem> sourceSystem = CriteriaValue.unspecified();
	private CriteriaValue<RuleNumberType> ruleNumberType = CriteriaValue.unspecified();
	private CriteriaValue<Long> ruleNumberFrom = CriteriaValue.unspecified();
	private CriteriaValue<Long> ruleNumberThru = CriteriaValue.unspecified();
    private CriteriaValue<Long> ruleNumber = CriteriaValue.unspecified();
	private CriteriaValue<IdentityType> personType = CriteriaValue.unspecified();
	private CriteriaValue<String> person = CriteriaValue.unspecified();
	private CriteriaValue<String> freeText = CriteriaValue.unspecified();
	
	public ResponsibilityCriteria() {}
	/**
	 * creates a criteria object from an example.  IF a value is null in the example, it will
	 * use the NULL criteria which will match the property with NULL.
	 * @param s
	 */
	public ResponsibilityCriteria(Responsibility s) {
		sourceSystem = CriteriaValue.orNull(s.getSourceSystem());
		ruleNumberType = CriteriaValue.orNull(s.getRuleNumberType());
		ruleNumberFrom = CriteriaValue.orNull(s.getRuleNumberFrom());
		ruleNumberThru = CriteriaValue.orNull(s.getRuleNumberThru());
		person = CriteriaValue.orNull(s.getResponsiblePersonId());
		personType = CriteriaValue.orNull(s.getResponsiblePersonType());
	}
	
	public CriteriaValue<SourceSystem> getSourceSystem() {
		return sourceSystem;
	}
	
	public CriteriaValue<String> getFreeText() {
		return freeText;
	}
	public void setFreeText(String value) {
		this.freeText = CriteriaValue.orUnspecified(StringUtils.trimToNull(value));
	}
	public void setSourceSystem(SourceSystem value) {
		this.sourceSystem = CriteriaValue.orUnspecified(value);
	}
	
	public CriteriaValue<Long> getRuleNumber() {
		return ruleNumber;
	}
	
	public void setRuleNumber(Long value) {
		this.ruleNumber = CriteriaValue.orUnspecified(value);
	}
	
	public void setRuleNumberType(CriteriaValue<RuleNumberType> ruleNumberType) {
		this.ruleNumberType = ruleNumberType;
	}
	
	public void setRuleNumberFrom(CriteriaValue<Long> ruleNumberFrom) {
		this.ruleNumberFrom = ruleNumberFrom;
	}
	
	public void setRuleNumberThru(CriteriaValue<Long> ruleNumberThru) {
		this.ruleNumberThru = ruleNumberThru;
	}
	
	public CriteriaValue<RuleNumberType> getRuleNumberType() {
		return ruleNumberType;
	}
	
	public CriteriaValue<Long> getRuleNumberFrom() {
		return ruleNumberFrom;
	}
	
	public CriteriaValue<Long> getRuleNumberThru() {
		return ruleNumberThru;
	}
	
	public CriteriaValue<IdentityType> getPersonType() {
		return personType;
	}
	
	public void setPersonType(IdentityType value) {
		this.personType = CriteriaValue.orUnspecified(value);
	}
	
	public CriteriaValue<String> getPerson() {
		return person;
	}
	
	public void setPerson(String value) {
		this.person = CriteriaValue.orUnspecified(StringUtils.trimToNull(value));
	}

	
}
