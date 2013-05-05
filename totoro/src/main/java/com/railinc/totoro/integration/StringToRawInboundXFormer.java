package com.railinc.totoro.integration;

import org.springframework.integration.Message;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.integration.transformer.Transformer;

import com.railinc.totoro.domain.InboundSource;
import com.railinc.totoro.domain.RawInboundMessage;

public class StringToRawInboundXFormer implements Transformer {

	@Override
	public Message<?> transform(Message<?> message) {
		RawInboundMessage m = new RawInboundMessage();
		m.setSource(InboundSource.MDMException);
		m.setData(message.getPayload().toString());
		return MessageBuilder.fromMessage(message).withPayload(m).build();
	}

}
