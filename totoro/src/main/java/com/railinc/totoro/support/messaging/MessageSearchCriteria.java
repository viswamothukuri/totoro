package com.railinc.totoro.support.messaging;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import org.apache.commons.lang.StringUtils;

import com.railinc.totoro.domain.InboundSource;
import com.railinc.totoro.domain.YesNo;
import com.railinc.totoro.util.CriteriaWithPaging;

public class MessageSearchCriteria extends CriteriaWithPaging {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4907077624454865906L;

	private Collection<String> data = new ArrayList<String>();
	private Collection<InboundSource> sources = new ArrayList<InboundSource>();
	private Boolean processed = null; // null means ignore the criteria.
	
	public void setProcessed(boolean b) {
		this.processed = b;
	}
	public void addData(String s) {
		if (StringUtils.isNotBlank(s)) {
			data.add(s);
		}
	}
	public void addInboundSource(InboundSource s) {
		if (s != null) {
			sources.add(s);
		}
	}
	public boolean hasInboundSourceCriteria() {
		return ! sources.isEmpty();
	}
	public boolean hasDataCriteria() {
		return ! data.isEmpty();
	}
	public boolean hasProcessedCriteria() {
		return processed != null;
	}
	public Collection<InboundSource> getInboundSourceCriteria() {
		return Collections.unmodifiableCollection(this.sources);
	}
	
	public Collection<String> getDataCriteria() {
		return Collections.unmodifiableCollection(this.data);
	}
	
	public YesNo getProcessedCriteria() {
		return YesNo.fromBoolean(this.processed);
	}
	
}
