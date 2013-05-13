package com.railinc.totoro.domain;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Version;

import com.google.common.base.Optional;

@Entity
@Table(name="RESPONSIBILITY")
public class Responsibility implements SoftDelete, Auditable {
	@Override
	public String toString() {
		return "Responsibility [id=" + id + ", version=" + version
				+ ", sourceSystem=" + sourceSystem + ", ruleNumber="
				+ ruleNumber + ", responsiblePerson=" + responsiblePerson
				+ ", note=" + note + ", auditData=" + auditData
				+ ", deleted=" + deleted + "]";
	}

	public static final int MAX_LENGTH_RULENUMBER = 20;
	
	@Id
	@GeneratedValue
	@Column(name="RP_MAP_ID")
	private Long id;
	
	@Version
    @Column(name="VERSION")
    private Integer version;
	
	
	
	public static final String PROPERTY_SOURCESYSTEM = "sourceSystem";
	@ManyToOne(optional=true)
	@JoinColumn(name="SS_ID", nullable=true)
	SourceSystem sourceSystem;

	public static final String PROPERTY_RULENUMBER = "ruleNumber";
	/**
	 * i hesitate to do it like this, but it feels like a string column
	 * will give us something easier to deal with programatically than multiple 
	 * database columns...
	 */
	@Basic(optional=true)
	@Column(name="RULE_NUM",nullable=true,length=MAX_LENGTH_RULENUMBER)
    private String ruleNumber;

	public static final String PROPERTY_RESPONSIBLE_PERSON_TYPE = "responsiblePerson." + Identity.PROPERTY_TYPE;
	public static final String PROPERTY_RESPONSIBLE_PERSON_ID = "responsiblePerson." + Identity.PROPERTY_ID;
	@Embedded
	private Identity responsiblePerson = new Identity();


	@Embedded
	private Note note = new Note();
	
	@Embedded
	private AuditData auditData = new AuditData();
	
	@Basic
	@Enumerated(value=EnumType.STRING)
	@Column(name="DELETED",nullable=false,updatable=true,length=1)
	private YesNo deleted = YesNo.N;

	
	public boolean isDefaultSourceSystemMatch() {
		return sourceSystem == null;
	}
	

	
	public boolean matches(Long rulenumber) {
		if (isDefaultRuleNumberMatch()) { return true; }
		throw new RuntimeException("Not implemented");
	}
	
	public boolean isDefaultRuleNumberMatch() {
		return ruleNumber == null;
	}

	
	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public Integer getVersion() {
		return version;
	}


	public void setVersion(Integer version) {
		this.version = version;
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
		this.ruleNumber = ruleNumber;
	}


	
	public Identity getResponsiblePerson() {
		return responsiblePerson;
	}


	public void setResponsiblePerson(Identity value) {
		if (responsiblePerson == null) {
			value = new Identity();
		}
		this.responsiblePerson = value;
	}


	public IdentityType getResponsiblePersonType() {
		return responsiblePerson.getType();
	}


	public void setResponsiblePersonType(IdentityType value) {
		responsiblePerson.setType(value);
	}


	public String getResponsiblePersonId() {
		return responsiblePerson.getId();
	}


	public void setResponsiblePersonId(String responsiblePersonId) {
		this.responsiblePerson.setId(responsiblePersonId);
	}


	@Override
	public boolean isDeleted() {
		return this.deleted.toBoolean();
	}
	
	@Override
	public void delete() {
		this.deleted = YesNo.Y;
	}

	@Override
	public void undelete() {
		this.deleted = YesNo.N;
	}

	@Override
	public AuditData getAuditData() {
		this.auditData = Optional.fromNullable(this.auditData).or(new AuditData());
		return this.auditData;
	}

	public void setAuditData(AuditData value) {
		this.auditData = value;
	}
	
	public Note getNote() {
		this.note = Optional.fromNullable(note).or(new Note());
		return this.note;
	}
	
	public void setNote(Note note) {
		this.note = note;
	}

	public YesNo getDeleted() {
		return deleted;
	}

	public void setDeleted(YesNo deleted) {
		this.deleted = deleted;
	}


	public String getNoteText() {
		return getNote().getText();
	}
	public void setNoteText(String v) {
		setNote(new Note(v));
	}
	
	
}
