package com.railinc.totoro.responsibility;

import static org.apache.commons.lang.StringUtils.trimToNull;

import javax.validation.constraints.NotNull;

import com.railinc.totoro.domain.AuditData;
import com.railinc.totoro.domain.ResponsiblePersonType;
import com.railinc.totoro.domain.SourceSystem;

public class ResponsibilityForm {

	@NotNull
	private Long id;

	@NotNull
	SourceSystem sourceSystem;

	@NotNull
    private Long ruleNumber;

	@NotNull
	private ResponsiblePersonType personType;

	private String person;
	
	private boolean deleted;
	
	private AuditData auditData = new AuditData();
	
	private int version;
	
	public AuditData getAuditData() {
		return auditData;
	}

	public void setAuditData(AuditData auditData) {
		this.auditData = (AuditData) auditData.clone();
	}

	public ResponsibilityForm() {
		id = 0L;
		version = 0;
	}
	
	
	
	public boolean isDeleted() {
		return deleted;
	}

	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}

	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

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
		this.person = trimToNull(person);
	}

	public void setVersion(int version) {
		this.version = version;
	}


}
