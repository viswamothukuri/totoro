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

	@Basic
	@Column(name="RULE_NUM",nullable=false)
    private Long ruleNumber;

	
	@Column(name="RESP_PERSON_TYPE",nullable=false)
	@Enumerated(EnumType.STRING)
	private ResponsiblePersonType responsiblePersonType;

	@Column(name="RESP_PERSON_ID",nullable=true,length=100)
	private String responsiblePersonId;

	
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


	public Long getRuleNumber() {
		return ruleNumber;
	}


	public void setRuleNumber(Long ruleNumber) {
		this.ruleNumber = ruleNumber;
	}


	public ResponsiblePersonType getResponsiblePersonType() {
		return responsiblePersonType;
	}


	public void setResponsiblePersonType(ResponsiblePersonType responsiblePersonType) {
		this.responsiblePersonType = responsiblePersonType;
	}


	public String getResponsiblePersonId() {
		return responsiblePersonId;
	}


	public void setResponsiblePersonId(String responsiblePersonId) {
		this.responsiblePersonId = responsiblePersonId;
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
