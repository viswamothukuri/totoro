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
	@Id
	@GeneratedValue
	@Column(name="RP_MAP_ID")
	private Long id;
	
	@Version
    @Column(name="VERSION")
    private Integer version;
	
	@ManyToOne(optional=false)
	@JoinColumn(name="SS_ID", nullable=true)
	SourceSystem sourceSystem;

	public boolean isDefaultSourceSystemMatch() {
		return sourceSystem == null;
	}
	
	/**
	 * i hesitate to do it like this, but it feels like a string column
	 * will give us something easier to deal with programatically than multiple 
	 * database columns...
	 */
	@Basic(optional=true)
	@Column(name="RULE_NUM",nullable=true)
    private String ruleNumber;
	
	public boolean matches(Long rulenumber) {
		if (isDefaultRuleNumberMatch()) { return true; }
		throw new RuntimeException("Not implementd");
	}
	
	public boolean isDefaultRuleNumberMatch() {
		return ruleNumber == null;
	}

	
	@Embedded
	private Identity responsiblePerson = new Identity();

	
	@Embedded
	private AuditData auditData = new AuditData();
	
	@Basic
	@Enumerated(value=EnumType.STRING)
	@Column(name="DELETED",nullable=false,updatable=true,length=1)
	private YesNo deleted = YesNo.N;

	
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


	public ResponsiblePersonType getResponsiblePersonType() {
		return responsiblePerson.getResponsiblePersonType();
	}


	public void setResponsiblePersonType(ResponsiblePersonType value) {
		responsiblePerson.setResponsiblePersonType(value);
	}


	public String getResponsiblePersonId() {
		return responsiblePerson.getResponsiblePersonId();
	}


	public void setResponsiblePersonId(String responsiblePersonId) {
		this.responsiblePerson.setResponsiblePersonId(responsiblePersonId);
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

	public void setAuditData(AuditData auditData) {
		this.auditData = auditData;
	}

	public YesNo getDeleted() {
		return deleted;
	}

	public void setDeleted(YesNo deleted) {
		this.deleted = deleted;
	}
	
	
}
