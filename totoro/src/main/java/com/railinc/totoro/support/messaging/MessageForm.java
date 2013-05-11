package com.railinc.totoro.support.messaging;

import com.railinc.totoro.domain.AuditData;

public class MessageForm {

	private Long identifier;
	private boolean processed;
	private String source;
	private String data;
	private AuditData auditData = new AuditData();
	public Long getIdentifier() {
		return identifier;
	}
	public void setIdentifier(Long identifier) {
		this.identifier = identifier;
	}
	public boolean isProcessed() {
		return processed;
	}
	public void setProcessed(boolean processed) {
		this.processed = processed;
	}
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}
	public String getData() {
		return data;
	}
	public void setData(String data) {
		this.data = data;
	}
	public AuditData getAuditData() {
		return auditData;
	}
	public void setAuditData(AuditData v) {
		if (v != null) {
			this.auditData = (AuditData) v.clone();
		}
	}
	
	
}
