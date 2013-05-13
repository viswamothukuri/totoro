package com.railinc.totoro.domain;

import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Table(name="DATA_EXCEPTION")
@Entity
public class DataException {


	@Basic(optional=false)
	@Column(name="EXCPTN_CREATE_TS",nullable=false)
	@Temporal(TemporalType.TIMESTAMP)
	@NotNull
	private Date exceptionCreated;

	@ManyToOne(optional=false)
	@JoinColumn(name="EDW_SRC_NAME", nullable=false)
	@NotNull
	SourceSystem sourceSystem;
	
	@Basic
	@Column(name="EDW_SRC_KEY_COLUMN",length=50,nullable=false)
	@NotNull
	@Size(min=1,max=50)
    private String sourceSystemKeyColumn;

	@Basic
	@Column(name="EDW_SRC_ID",length=50,nullable=false)
	@NotNull
	@Size(min=1,max=50)
    private String sourceSystemKeyValue;

	@Basic
	@Column(name="EDW_SRC_VALUE",length=50,nullable=false)
	@NotNull
	@Size(min=1,max=50)
	private String sourceSystemValue; // assumed wrong

	@Basic
	@Column(name="EDW_SRC_INFO",length=1024,nullable=true)
	@Size(max=1024)
    private String sourceSystemObjectData; // assumed wrong
	
	@Basic
	@Column(name="EDW_SRC_KEY",length=50,nullable=false)
	@NotNull
	@Size(min=1,max=50)
    private String sourceSystemRecordIdentifier;

	@Basic(optional=false)
	@Column(name="EXCPTN_CD",nullable=false)
	private Long code;

	@Basic(optional=true)
	@Column(name="EXCPTN_DESC",nullable=true)
	private String description;

	@Basic(optional=false)
	@Column(name="MDM_EXCPTN_TYPE",nullable=false)
	private String mdmObjectType;

	@Basic(optional=false)
	@Column(name="MDM_EXCPTN_COL_NAME",nullable=false)
	private String mdmObjectAttribute;
	
	@Basic(optional=false)
	@Column(name="MDM_VALUE",nullable=false)
	private String mdmAttributevalue;
	


	
	
	// ------- totoro specific
	@Version
    @Column(name="VERSION")
    private Integer version;
		

	@Id
	@GeneratedValue
	@Column(name="EXCPTN_ID")
	private Long id;
	
	
	@ManyToOne(optional=false)
	@JoinColumn(name="MSG_ID", nullable=false)
	private RawInboundMessage source;

	/**
	 * so, we need to determine who is reponsibile for a data exception.  this may not be the 
	 * person who ultimately handles the exception, but we must identify the person
	 * so that we can build tasks from the data exceptions.  
	 */
	@Embedded
	private IdentityType responsiblePerson;
	
	@ManyToOne(optional=true)
	@JoinColumn(name="TASK_ID", nullable=true)
	private Task task;

	@Basic
	@Column(name="BUNDLE_NM",nullable=true)
	private String bundleName;


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getExceptionCreated() {
		return exceptionCreated;
	}

	public void setExceptionCreated(Date exceptionCreated) {
		this.exceptionCreated = exceptionCreated;
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

	public String getSourceSystemKeyColumn() {
		return sourceSystemKeyColumn;
	}

	public void setSourceSystemKeyColumn(String sourceSystemKeyColumn) {
		this.sourceSystemKeyColumn = sourceSystemKeyColumn;
	}

	public String getSourceSystemKeyValue() {
		return sourceSystemKeyValue;
	}

	public void setSourceSystemKeyValue(String sourceSystemKeyValue) {
		this.sourceSystemKeyValue = sourceSystemKeyValue;
	}

	public String getSourceSystemValue() {
		return sourceSystemValue;
	}

	public void setSourceSystemValue(String sourceSystemValue) {
		this.sourceSystemValue = sourceSystemValue;
	}

	public String getSourceSystemObjectData() {
		return sourceSystemObjectData;
	}

	public void setSourceSystemObjectData(String sourceSystemObjectData) {
		this.sourceSystemObjectData = sourceSystemObjectData;
	}

	public String getSourceSystemRecordIdentifier() {
		return sourceSystemRecordIdentifier;
	}

	public void setSourceSystemRecordIdentifier(String sourceSystemRecordIdentifier) {
		this.sourceSystemRecordIdentifier = sourceSystemRecordIdentifier;
	}

	public Long getCode() {
		return code;
	}

	public void setCode(Long code) {
		this.code = code;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getMdmObjectType() {
		return mdmObjectType;
	}

	public void setMdmObjectType(String mdmObjectType) {
		this.mdmObjectType = mdmObjectType;
	}

	public String getMdmObjectAttribute() {
		return mdmObjectAttribute;
	}

	public void setMdmObjectAttribute(String mdmObjectAttribute) {
		this.mdmObjectAttribute = mdmObjectAttribute;
	}

	public String getMdmAttributevalue() {
		return mdmAttributevalue;
	}

	public void setMdmAttributevalue(String mdmAttributevalue) {
		this.mdmAttributevalue = mdmAttributevalue;
	}

	public Task getTask() {
		return task;
	}

	public void setTask(Task task) {
		this.task = task;
	}

	public String getBundleName() {
		return bundleName;
	}

	public void setBundleName(String bundleName) {
		this.bundleName = bundleName;
	}

	public RawInboundMessage getSource() {
		return source;
	}

	public void setSource(RawInboundMessage source) {
		this.source = source;
	}


	
}
