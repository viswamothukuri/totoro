package com.railinc.totoro.support.messaging;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import org.apache.commons.lang.StringUtils;

import com.railinc.totoro.domain.InboundSource;
import com.railinc.totoro.domain.YesNo;
import com.railinc.totoro.util.CriteriaValue;
import com.railinc.totoro.util.CriteriaWithPaging;

public class MessageSearchCriteria extends CriteriaWithPaging {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4907077624454865906L;

	private CriteriaValue<ArrayList<String>> data = CriteriaValue.unspecified();
	private CriteriaValue<ArrayList<InboundSource>> sources = CriteriaValue.unspecified();
	private CriteriaValue<YesNo> processed = CriteriaValue.unspecified();
	
	
	public void setProcessed(boolean b) {
		this.processed = CriteriaValue.of(YesNo.fromBoolean(b));
	}
	
	public void addData(String s) {
		if (StringUtils.isNotBlank(s)) {
			if (data.isUnspecifiedOrNull()) {
				data = CriteriaValue.of(new ArrayList<String>());
			}
			data.value().add(s);
		}
	}
	public void addInboundSource(InboundSource s) {
		if (s != null) {
			if (sources.isUnspecifiedOrNull()) {
				sources = CriteriaValue.of(new ArrayList<InboundSource>());
			}
			sources.value().add(s);
		}
	}
	
	
	public CriteriaValue<ArrayList<String>> getData() {
		return data;
	}

	public CriteriaValue<ArrayList<InboundSource>> getSources() {
		return sources;
	}

	public boolean hasProcessedCriteria() {
		return processed.isSpecified();
	}
	
	public CriteriaValue<YesNo> getProcessed() {
		return processed;
	}

	
}
