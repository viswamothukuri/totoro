package com.railinc.totoro.support.messaging;

import java.beans.PropertyEditorSupport;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.web.bind.WebDataBinder;

import com.google.common.base.Functions;
import com.google.common.collect.Lists;
import com.railinc.totoro.domain.InboundSource;
import com.railinc.totoro.util.PagedSearchForm;

public class MessageSearchForm extends PagedSearchForm<MessageSearchCriteria, MessageForm>{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 304639385271754558L;
	
	private InboundSource source;
	private String query;
	private Boolean processed;


	
	@Override
	public MessageSearchCriteria getCriteria() {
		MessageSearchCriteria c = new MessageSearchCriteria();
		// build up the criteria from the fields
		if (processed != null) {
			c.setProcessed(this.processed);
		}
		if (source != null){
			c.addInboundSource(source);
		}
		if (StringUtils.isNotBlank(query)) {
			c.addData(query);
		}
		return c;
	}


	public InboundSource getSource() {
		return source;
	}
	
	public void setSource(InboundSource source) {
		this.source = source;
	}
	
	public String getQuery() {
		return query;
	}
	
	public void setQuery(String query) {
		this.query = query;
	}
	
	public Boolean getProcessed() {
		return processed;
	}
	
	public void setProcessed(Boolean value) {
		this.processed = value;
	}
	
	public Collection<String> getSourceOptions() {
		List<InboundSource> asList = Arrays.asList(InboundSource.values());
		return Lists.transform(asList, Functions.toStringFunction());
	}
	
	public Collection<String> getProcessedOptions() {
		return Arrays.asList(new String[]{"Yes","No"});
	}
	
	public static final void initBinder(WebDataBinder binder) {
		binder.registerCustomEditor(Boolean.class, "processed", TRISTATE_BOOLEAN);
		binder.registerCustomEditor(InboundSource.class, "source", NULLABLE_SOURCE);
	}
	
	public static final PropertyEditorSupport NULLABLE_SOURCE = new PropertyEditorSupport() {
		
		@Override
		public String getAsText() {
			return String.valueOf(getValue());
		}

		@Override
		public void setAsText(String text) throws IllegalArgumentException {
			try {
				setValue(InboundSource.valueOf(text));
			} catch (Exception e) {
				setValue(null);
			}
		}
	};
	
	
	public static final PropertyEditorSupport TRISTATE_BOOLEAN = new PropertyEditorSupport() {
		@Override
		public String getAsText() {
			Boolean b = (Boolean) getValue();
			return (b == null) ? "" : (b.booleanValue() ? "Yes" : "No");
		}
		@Override
		public void setAsText(String text) throws IllegalArgumentException {
			if (StringUtils.equals("Yes", text)) {
				setValue(Boolean.TRUE);
			} else if (StringUtils.equals("No", text)) {
				setValue(Boolean.FALSE);
			} else {
				setValue(null);
			}
		}
	};
	
}
