package com.railinc.totoro.integration;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validation;
import javax.validation.Validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.integration.Message;
import org.springframework.integration.MessageRejectedException;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.integration.transformer.Transformer;

import com.google.common.base.Function;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.railinc.totoro.domain.DataException;
import com.railinc.totoro.domain.RawInboundMessage;
import com.railinc.totoro.domain.SourceSystem;
import com.railinc.totoro.domain.YesNo;
import com.railinc.totoro.integration.msg.InboundMDMExceptionMessage;
import com.railinc.totoro.sourcesystem.SourceSystemService;

public class RawInboundMessageToExceptionXFormer implements Transformer, Function<InboundMDMExceptionMessage, DataException> {

	@Autowired
	SourceSystemService service;
	
	/**
	 * translate the inbound JSON to a DataException
	 */
	@Override
	public Message<?> transform(Message<?> message) {
		RawInboundMessage m = (RawInboundMessage) message.getPayload();
		
		Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
		InboundMDMExceptionMessage in = new GsonBuilder()
			.setDateFormat("yyyy-mm-dd HH:MM")
			.create()
			.fromJson(m.getData(), InboundMDMExceptionMessage.class);
		
		Set<ConstraintViolation<InboundMDMExceptionMessage>> violations = validator.validate(in);
		if (!violations.isEmpty()) {
			return MessageBuilder.fromMessage(message).withPayload(new ConstraintViolationException(violations)).build();
		}
		
		
		DataException dataException = this.apply(in);
		dataException.setSource(m);
		
		
		Set<ConstraintViolation<DataException>> validate = validator.validate(dataException);
		if (!validate.isEmpty()) {
			return MessageBuilder.fromMessage(message).withPayload(new ConstraintViolationException(violations)).build();
		}

		// all is good
		m.setProcessed(YesNo.Y);
		
		return MessageBuilder.fromMessage(message).withPayload(dataException).build();
	}

	public DataException apply(InboundMDMExceptionMessage in) {
		DataException d = new DataException();
		SourceSystem sourceSystem = service.get(in.getSourceSystem());
		d.setSourceSystem(sourceSystem);
		d.setCode(in.getCode());
		d.setDescription(in.getDescription());
		d.setExceptionCreated(in.getCreated());
		d.setMdmAttributevalue(in.getMdmAttributevalue());
		d.setMdmObjectAttribute(in.getMdmObjectAttribute());
		d.setMdmObjectType(in.getMdmObjectType());
		d.setSourceSystemKeyColumn(in.getSourceSystemKeyColumn());
		d.setSourceSystemKeyValue(in.getSourceSystemKeyValue());
		d.setSourceSystemObjectData(in.getSourceSystemObjectData());
		d.setSourceSystemRecordIdentifier(in.getSourceSystemRecordIdentifier());
		d.setSourceSystemValue(in.getSourceSystemValue());
		return d;
	}
}
