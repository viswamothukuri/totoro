package com.railinc.totoro.integration;

import io.iron.ironmq.Client;
import io.iron.ironmq.EmptyQueueException;
import io.iron.ironmq.Queue;

import java.io.IOException;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.context.Lifecycle;
import org.springframework.integration.Message;
import org.springframework.integration.core.MessageSource;
import org.springframework.integration.message.GenericMessage;

import com.google.common.base.Throwables;
import com.google.common.collect.Maps;


public class IronMQInboundChannel implements MessageSource<String>, Lifecycle {
	private boolean running = false;
	
	private Logger logger = LoggerFactory.getLogger(IronMQInboundChannel.class);
	private transient Client client;
	private transient Queue queue;
	
	private String queueName;
	private String token;
	private String projectId;
	
	
	public String getQueueName() {
		return queueName;
	}

	public void setQueueName(String queueName) {
		this.queueName = queueName;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getProjectId() {
		return projectId;
	}

	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}
	
	

	public Client getClient() {
		return client;
	}
	public void setClient(Client client) {
		this.client = client;
	}
	public Queue getQueue() {
		return queue;
	}
	public void setQueue(Queue queue) {
		this.queue = queue;
	}
	@Override
	public Message<String> receive() {
		if (!isRunning()) return null;
		try {
			io.iron.ironmq.Message message = queue().get();
			Map<String,Object> headers = Maps.newHashMap();
			
			headers.put("id", message.getId());
			headers.put("timeout", message.getTimeout());
			headers.put("delay", message.getDelay());
			headers.put("expiresIn",message.getExpiresIn());
			
			Message<String> m = new GenericMessage<String>(message.getBody(), headers);
			
			queue().deleteMessage(message);
			return m;
		} catch (EmptyQueueException e) {
			return null;
		} catch (IOException e) {
			throw Throwables.propagate(e);
		} finally {
			
		}
	}

	private synchronized Queue queue() {
		if (queue == null) { queue =client().queue(getQueueName()); }
		return queue;
	}
	
	private synchronized Client client() {
		if (client == null) { client = new Client(projectId, token); }
		return client;
	}
	
	@Override
	public boolean isRunning() {
		return running;
	}

	@Override
	public synchronized void start() {
		logger.info("Pull messages from queue {}", queue());
		running = true;
	}

	@Override
	public synchronized void stop() {
		running = false;
		queue = null;
		client = null;
	}

}
