package com.railinc.totoro.domain;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

public class Identity {

	@Column(name="RESP_PERSON_TYPE",nullable=false)
	@Enumerated(EnumType.STRING)
	private ResponsiblePersonType responsiblePersonType;

	@Column(name="RESP_PERSON_ID",nullable=true,length=100)
	private String responsiblePersonId;

	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime
				* result
				+ ((responsiblePersonId == null) ? 0 : responsiblePersonId
						.hashCode());
		result = prime
				* result
				+ ((responsiblePersonType == null) ? 0 : responsiblePersonType
						.hashCode());
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
		Identity other = (Identity) obj;
		if (responsiblePersonId == null) {
			if (other.responsiblePersonId != null)
				return false;
		} else if (!responsiblePersonId.equals(other.responsiblePersonId))
			return false;
		if (responsiblePersonType != other.responsiblePersonType)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Identity [responsiblePersonType=" + responsiblePersonType
				+ ", responsiblePersonId=" + responsiblePersonId + "]";
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
	
	
	
}
