package com.railinc.totoro.support.messaging;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.railinc.totoro.domain.RawInboundMessage;
import com.railinc.totoro.util.PagedCollection;

@Service
@Transactional
public interface MessageService {
	PagedCollection<RawInboundMessage> all(MessageSearchCriteria criteria);
	void save(RawInboundMessage s);
	void delete(RawInboundMessage s);
	RawInboundMessage get(Long id);
	void sendMessage(String data);
}
