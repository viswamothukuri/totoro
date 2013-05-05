package com.railinc.totoro.integration.msg;

import java.util.Date;

import com.google.gson.annotations.SerializedName;

public class InboundMDMExceptionMessage {

	@SerializedName("created")
	private Date created;

	@SerializedName("sourcesystem")
	private String sourceSystem;
	
	@SerializedName("sourcekeycol")
    private String sourceSystemKeyColumn;

	@SerializedName("sourcekeyvalue")
	private String sourceSystemKeyValue;

	@SerializedName("sourcevalue")
    private String sourceSystemValue; // assumed wrong

	@SerializedName("sourceinfo")
    private String sourceSystemObjectData;
	
	@SerializedName("sourcerecordid")
    private String sourceSystemRecordIdentifier;

	@SerializedName("code")
	private Long code;
	
	@SerializedName("description")
	private String description;

	@SerializedName("type")
	private String mdmObjectType;

	@SerializedName("attr")
	private String mdmObjectAttribute;
	
	@SerializedName("value")
	private String mdmAttributevalue;

	
	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	public String getSourceSystem() {
		return sourceSystem;
	}

	public void setSourceSystem(String sourceSystem) {
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

	
}
