package com.railinc.totoro.responsibility;

import static org.apache.commons.lang.StringUtils.trimToNull;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.apache.commons.lang.StringUtils;

import com.railinc.totoro.domain.AuditData;
import com.railinc.totoro.domain.IdentityType;
import com.railinc.totoro.domain.Note;
import com.railinc.totoro.domain.Responsibility;
import com.railinc.totoro.domain.SourceSystem;

public class ResponsibilityForm {

	@NotNull
	private Long id;

	private SourceSystem sourceSystem;

	@Min(1)
	@Max(Responsibility.MAX_LENGTH_RULENUMBER)
	@Pattern(regexp="-?\\d+,-?\\d+")//,message="Must be either blank or a numeric range like 1,3"
    private String ruleNumber;

	@NotNull
	private IdentityType personType;

	private String person;
	
	private boolean deleted;
	
	@Max(value=Note.MAX_LENGTH_NOTES)
	private String note;
	
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
	
	
	
	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = StringUtils.trimToNull(note);
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

	public String getRuleNumber() {
		return ruleNumber;
	}

	public void setRuleNumber(String ruleNumber) {
		this.ruleNumber = StringUtils.trimToNull(ruleNumber);
	}

	public IdentityType getPersonType() {
		return personType;
	}

	public void setPersonType(IdentityType personType) {
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
