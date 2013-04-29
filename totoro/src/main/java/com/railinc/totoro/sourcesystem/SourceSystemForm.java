package com.railinc.totoro.sourcesystem;

import static org.apache.commons.lang.StringUtils.trimToNull;
import static org.apache.commons.lang.StringUtils.upperCase;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.railinc.totoro.domain.AuditData;
import com.railinc.totoro.domain.SourceSystem;

public class SourceSystemForm {

	@NotNull
	@Size(min=1,max=10)
	String id;
	
	@NotNull
	@Size(min=1,max=50)
	String name;

	@NotNull
	private Integer version;
	
	private AuditData auditData;
	
	public AuditData getAuditData() {
		return auditData;
	}

	public void setAuditData(AuditData auditData) {
		this.auditData = auditData;
	}

	public SourceSystemForm() {
		version = 0;
	}
	
	public SourceSystemForm(SourceSystem ss) {
		this.id = ss.getIdentifier();
		this.name = ss.getName();
		this.version = ss.getVersion();
		this.auditData = (AuditData) ss.getAuditData().clone();
	}
	
	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = upperCase(id);
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = trimToNull(name);
	}
	
}
