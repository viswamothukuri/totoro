package com.railinc.totoro.integration;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.integration.Message;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.integration.transformer.Transformer;

import com.google.common.base.Optional;
import com.railinc.totoro.domain.InboundSource;
import com.railinc.totoro.domain.RawInboundMessage;

public class StringToRawInboundXFormer implements Transformer {
	public static final String SOURCE_HEADER = "source";

	private Logger logger = LoggerFactory.getLogger(StringToRawInboundXFormer.class);
	@Override
	public Message<?> transform(Message<?> message) {
		InboundSource source = Optional.fromNullable(message.getHeaders().get(SOURCE_HEADER, InboundSource.class))
			.or(InboundSource.Unknown);

		logger.debug("Inbound Source is {}", source);
		logger.debug("Creating RawInboundMessage with {}, {}", source, message.getPayload());
		
		RawInboundMessage m = new RawInboundMessage();
		m.setSource(source);
		m.setData(message.getPayload().toString());
		
		return MessageBuilder
				.withPayload(m)
				.copyHeaders(message.getHeaders())
				.build();
	}

}
