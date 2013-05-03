package com.railinc.totoro.integration;

import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.integration.Message;
import org.springframework.integration.MessagingException;
import org.springframework.integration.core.MessageHandler;
import org.springframework.transaction.annotation.Transactional;

import com.railinc.totoro.domain.InboundSource;
import com.railinc.totoro.domain.RawInboundMessage;

@Transactional
public class InboundMessageStorageHandler implements MessageHandler {
	private Logger logger = LoggerFactory.getLogger(InboundMessageStorageHandler.class);
	
	public InboundMessageStorageHandler() {
		logger.debug("New Instance : " + this);
	}
	
	@Autowired
	private SessionFactory sessionFactory;

	@Transactional
	@Override
	public void handleMessage(Message<?> arg0) throws MessagingException {
		RawInboundMessage m = new RawInboundMessage();
		m.setSource(InboundSource.MDMException);
		m.setData(arg0.getPayload().toString());
		sessionFactory.getCurrentSession().save(m);
	}
	
	@Required
	public void setSessionFactory(SessionFactory sf) {
		this.sessionFactory = sf;
	}

}
