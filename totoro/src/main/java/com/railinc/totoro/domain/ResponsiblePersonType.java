package com.railinc.totoro.domain;

public enum ResponsiblePersonType {
	SsoId("SSO User"),
	SsoRole("SSO Role"),
	LocalGroup("Local User Group"),
	LookupInContextData("Lookup in Source System Data"),
	ExternalEmailUser("External E-Mail"),
	AskSourceSystem("Ask the Source System");
	
	private String description;

	ResponsiblePersonType(String desc) {
		this.description = desc;
	}
	
	public String toString() {
		return description;
	}
	public static final ResponsiblePersonType find(String value) {
		for (ResponsiblePersonType t : values()) {
			if (t.name().equals(value) || t.description.equals(value)) {
				return t;
			}
		}
		return null;
	}
}
