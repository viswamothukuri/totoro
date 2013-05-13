package com.railinc.totoro.domain;

public enum IdentityType {
	SsoId("SSO User"),
	SsoRole("SSO Role"),
	LocalGroup("Local User Group"),
	LookupInContextData("Lookup in Source System Data"),
	ExternalEmailUser("External E-Mail"),
	AskSourceSystem("Ask the Source System");
	
	public static final int MAX_LENGTH = 20;
	
	private String description;

	static {
		int maxLength = maxLength();
		if (maxLength > MAX_LENGTH) {
			throw new RuntimeException("MAX_LENGTH constant is not large enough, should be " + maxLength);
		}
	}
	
	
	IdentityType(String desc) {
		this.description = desc;
	}
	
	private static final int maxLength() {
		int max = 0;
		for (IdentityType it : values()) {
			max = Math.max(max, it.name().length());
		}
		return max;
	}
	
	public String toString() {
		return description;
	}
	public static final IdentityType find(String value) {
		for (IdentityType t : values()) {
			if (t.name().equals(value) || t.description.equals(value)) {
				return t;
			}
		}
		return null;
	}
}
