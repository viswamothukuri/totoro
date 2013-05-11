package com.railinc.totoro.domain;

public enum YesNo {
	Y(true),N(false);
	private boolean e;

	YesNo(boolean equiv) {
		this.e = equiv;
	}
	boolean toBoolean() {
		return this.e;
	}
	public static YesNo fromBoolean(Boolean b) {
		if (b == null) {
			return null;
		}
		return b.booleanValue() ? Y : N;
	}
}
