package com.railinc.totoro.domain;

import static org.apache.commons.lang.StringUtils.defaultIfBlank;

import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.google.common.base.Throwables;

public class AuditData implements Cloneable {

	public static final int MAX_LENGTH_CREATED_BY = 25;

	public static final int MAX_LENGTH_UPDATED_BY = 25;

	@Override
	public Object clone() {
		try {
			return super.clone();
		} catch (CloneNotSupportedException e) {
			throw Throwables.propagate(e);
		}
	}

	@Basic
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="CREATED_TS",nullable=false,updatable=false)
	Date created = new Date();
	
	@Basic
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="UPDATED_TS",nullable=false)
	Date updated = new Date();
	
	@Basic
	@Column(name="CREATED_BY",nullable=false,updatable=false,length=MAX_LENGTH_CREATED_BY)
	String createdBy = "unknown";
	
	@Basic
	@Column(name="UPDATED_BY",nullable=false,length=MAX_LENGTH_UPDATED_BY)
	String updatedBy = "unknown";
	
	public void touchLastModified(String user) {
		updated = new Date();
		this.updatedBy = defaultIfBlank(user, "unknown");
	}

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	public Date getUpdated() {
		return updated;
	}

	public void setUpdated(Date updated) {
		this.updated = updated;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public String getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}

	
}
