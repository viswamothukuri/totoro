package com.railinc.totoro.domain;

import java.io.Serializable;

import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

import org.hibernate.EmptyInterceptor;
import org.hibernate.type.Type;

public class AuditListener extends EmptyInterceptor {

	@Override
	public boolean onFlushDirty(Object entity, Serializable id,
			Object[] currentState, Object[] previousState,
			String[] propertyNames, Type[] types) {
		if (entity instanceof Auditable) {
			updateLastModified((Auditable) entity);
		}
		return true;

	}

	@Override
	public boolean onSave(Object entity, Serializable id, Object[] state,
			String[] propertyNames, Type[] types) {
		if (entity instanceof Auditable) {
			updateLastModified((Auditable) entity);
		}
		return true;
	}

	@PreUpdate
	@PrePersist
	public void updateLastModified(Auditable auditable) {
		auditable.getAuditData().touchLastModified("whoever");
	}
}
