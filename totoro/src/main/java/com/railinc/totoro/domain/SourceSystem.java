package com.railinc.totoro.domain;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Version;

import com.google.common.base.Optional;

@Table(name="SOURCE_SYSTEM")
@Entity(name="sourcesystem")
@EntityListeners(value=AuditListener.class)
public class SourceSystem implements Auditable, SoftDelete {

	public static final String PROPERTY_ID = "identifier";
	public static final String PROPERTY_NAME = "name";
	public static final String PROPERTY_OUTBOUND_QUEUE = "outboundQueue";
	public static final String PROPERTY_DELETED = "deleted";
	public static final String DEFAULT_ORDER_BY_PROPERTY = PROPERTY_ID;
	
	@Id
    @Column(name="SS_ID")
	private String identifier;

	@Version
    @Column(name="VERSION")
    private Integer version;

	@Basic
	@Column(name="NAME")
	private String name;
	
	@Basic
	@Column(name="OUTBOUND_QUEUE")
	private String outboundQueue;
	

	@Embedded
	private AuditData audit = new AuditData();
	
	@Basic
	@Enumerated(value=EnumType.STRING)
	@Column(name="DELETED",nullable=false,updatable=true,length=1)
	private YesNo deleted = YesNo.N;
	

	public SourceSystem(){}
	public SourceSystem(String id, String name2) {
		setIdentifier(id);
		setName(name2);
	}

	public Integer getVersion() {
		return version;
	}
	
	public String getIdentifier() {
		return identifier;
	}

	public void setIdentifier(String identifier) {
		this.identifier = identifier;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public boolean isDeleted() {
		return this.deleted.toBoolean();
	}

	@Override
	public void undelete() {
		this.deleted = YesNo.N;
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

	public YesNo getDeleted() {
		return deleted;
	}

	public void setDeleted(YesNo deleted) {
		this.deleted = deleted;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

	public void delete() {
		this.deleted = YesNo.Y;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((identifier == null) ? 0 : identifier.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SourceSystem other = (SourceSystem) obj;
		if (identifier == null) {
			if (other.identifier != null)
				return false;
		} else if (!identifier.equals(other.identifier))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "SourceSystem [identifier=" + identifier + ", version="
				+ version + ", name=" + name + ", audit=" + audit
				+ ", deleted=" + deleted + "]";
	}
	public String getOutboundQueue() {
		return outboundQueue;
	}
	public void setOutboundQueue(String outboundQueue) {
		this.outboundQueue = outboundQueue;
	}
	
	
	

}
