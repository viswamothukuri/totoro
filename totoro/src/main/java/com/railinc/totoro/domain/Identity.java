package com.railinc.totoro.domain;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

public class Identity {
	
	public static final int MAX_LENGTH_ID = 100;
	
	
	public static final String PROPERTY_TYPE = "type";
	@Column(name="IDENTITY_TYPE",nullable=false,length=IdentityType.MAX_LENGTH)
	@Enumerated(EnumType.STRING)
	private IdentityType type;

	public static final String PROPERTY_ID = "id";
	
	@Column(name="IDENTITY_ID",nullable=true,length=MAX_LENGTH_ID)
	private String id;

	
	public Identity() {}
	
	public Identity(IdentityType type, String id) {
		this.type = type;
		this.id = id;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime
				* result
				+ ((id == null) ? 0 : id
						.hashCode());
		result = prime
				* result
				+ ((type == null) ? 0 : type
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
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (type != other.type)
			return false;
		return true;
	}



	@Override
	public String toString() {
		return "Identity [type=" + type + ", id=" + id + "]";
	}

	public IdentityType getType() {
		return type;
	}

	public void setType(IdentityType value) {
		this.type = value;
	}

	public String getId() {
		return id;
	}

	public void setId(String value) {
		this.id = value;
	}
	
	
	
}
