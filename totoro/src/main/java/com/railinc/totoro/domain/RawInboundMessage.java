package com.railinc.totoro.domain;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import com.google.common.base.Optional;

@Table(name="INBOUND_MESSAGE")
@Entity(name="inboundmessage")
public class RawInboundMessage implements Auditable {

	public static final String PROPERTY_ID = "identifier";
	public static final String PROPERTY_NAME = "name";
	public static final String PROPERTY_DELETED = "deleted";
	public static final String DEFAULT_ORDER_BY_PROPERTY = PROPERTY_ID;
	
	@Id
	@GeneratedValue
    @Column(name="MSG_ID")
	private Long identifier;

	@Basic
	@Enumerated(value=EnumType.STRING)
	@Column(name="PROCESSED",nullable=false,updatable=true,length=1)
	private YesNo processed = YesNo.N;
	
	@Basic
	@Enumerated(value=EnumType.STRING)
	@Column(name="SOURCE",nullable=false,updatable=false,length=15)
	private InboundSource source = null;
	
	@Basic
	@Column(name="DATA",nullable=false,updatable=true,length=2048)
	private String data;

	@Embedded
	private AuditData audit = new AuditData();
	
	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public Long getIdentifier() {
		return identifier;
	}

	public void setIdentifier(Long identifier) {
		this.identifier = identifier;
	}

	public YesNo getProcessed() {
		return processed;
	}

	public void setProcessed(YesNo processed) {
		this.processed = processed;
	}

	public InboundSource getSource() {
		return source;
	}

	public void setSource(InboundSource source) {
		this.source = source;
	}

	@Override
	public AuditData getAuditData() {
		this.audit = Optional.fromNullable(this.audit).or(new AuditData());
		return this.audit;
	}

	public AuditData getAudit() {
		return audit;
	}

	public void setAudit(AuditData audit) {
		this.audit = audit;
	}




	

}
