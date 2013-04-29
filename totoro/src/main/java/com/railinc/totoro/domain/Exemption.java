package com.railinc.totoro.domain;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Version;

@Entity
@Table(name="EXEMPTION")
public class Exemption {
	@Id
	@GeneratedValue
	@Column(name="EXEMPT_ID")
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

	@Basic
	@Column(name="RECORD_ID",length=50,nullable=false)
    private String recordIdentifier;

	
	@ManyToOne(optional=true)
	@JoinColumn(name="TASK_ID", nullable=true)
	private Task createdFromTask;
	
	
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

	public String getRecordIdentifier() {
		return recordIdentifier;
	}

	public void setRecordIdentifier(String recordIdentifier) {
		this.recordIdentifier = recordIdentifier;
	}

	
	
	
}
