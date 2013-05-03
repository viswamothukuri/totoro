package com.railinc.totoro.support.messaging;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;

public class MessageSubmissionForm {

	@Size(min=1,max=1024)
	String data;
	
	@Min(value=1)
	@Max(value=100)
	Integer times=1;
	

	public Integer getTimes() {
		return times;
	}

	public void setTimes(Integer v) {
		this.times = v;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}
	
	
}
