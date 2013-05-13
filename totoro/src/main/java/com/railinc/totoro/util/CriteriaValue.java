package com.railinc.totoro.util;



public class CriteriaValue<T> {

	private final T value;

	@SuppressWarnings("unchecked")
	public static <T> CriteriaValue<T> orNull(T value) {
		if (value == null ){ return (CriteriaValue<T>) Null.INSTANCE; }
		return new CriteriaValue<T>(value);
	}

	@SuppressWarnings("unchecked")
	public static <T> CriteriaValue<T> orUnspecified(T value) {
		if (value == null ){ return (CriteriaValue<T>) NotSpecified.INSTANCE; }
		return new CriteriaValue<T>(value);
	}
	

	public static <T> CriteriaValue<T> of(T b) {
		return new CriteriaValue<T>(b);
	}

	protected CriteriaValue(){
		value = null;
	}
	
	public T value() {
		return this.value;
	}
	
	public CriteriaValue(T v){ 
		this.value = v;
	}
	
	public boolean isNull() {
		return false;
	}

	public boolean isSpecified() {
		return true;
	}
	
	public boolean isUnspecified() {
		return ! isSpecified();
	}	
	
	private boolean isNotNull() {
		return ! isNull();
	}


	final static class Null extends CriteriaValue<Object> {
		static final Null INSTANCE = new Null();

		public boolean isNull() {
			return true;
		}
	}

	@SuppressWarnings("unchecked")
	public static <T> CriteriaValue<T> unspecified() {
		return (CriteriaValue<T>) NotSpecified.INSTANCE;
	}

	final static class NotSpecified extends CriteriaValue<Object> {
		public static final NotSpecified INSTANCE = new NotSpecified();
	
		public boolean isSpecified() {
			return false;
		}

	}

	public boolean isSpecifiedAndNull() {
		return isSpecified() && isNull();
	}

	public boolean isSpecifiedAndNotNull() {
		return isSpecified() && isNotNull();
	}

	public boolean isUnspecifiedOrNull() {
		return isNull() || isUnspecified();
	}




}
