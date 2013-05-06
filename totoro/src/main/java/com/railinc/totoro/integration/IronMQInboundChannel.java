package com.railinc.totoro.integration;

import io.iron.ironmq.Client;
import io.iron.ironmq.EmptyQueueException;
import io.iron.ironmq.Queue;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.Lifecycle;
import org.springframework.integration.Message;
import org.springframework.integration.core.MessageSource;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.transaction.support.TransactionSynchronizationAdapter;
import org.springframework.transaction.support.TransactionSynchronizationManager;
import org.springframework.util.Assert;

import com.google.common.base.Throwables;


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
			Queue q = queue();
			io.iron.ironmq.Message message = q.get();

			Message<String> m = MessageBuilder.withPayload(message.getBody())
				.setHeader("timeout", message.getTimeout())
				.setHeader("delay", message.getDelay())
				.setHeader("expiresIn", message.getExpiresIn())
				.build();

			registerSyncOrComplete(q, message);
			
			return m;
		} catch (EmptyQueueException e) {
			return null;
		} catch (IOException e) {
			throw Throwables.propagate(e);
		}
	}

	private void registerSyncOrComplete(Queue q, io.iron.ironmq.Message message) {
		if (TransactionSynchronizationManager.isSynchronizationActive()) {
			logger.debug("Registering TransactionSynchronization for message id {}", message.getId());
			TransactionSynchronizationManager.registerSynchronization(new IronMqMessageTransactionSynchronization(q, message));
			return;
		} 

		logger.debug("TransactionSynchronization is not active, remove message from queue.");
		try {
			queue.deleteMessage(message);
			logger.debug("TransactionSynchronization is not active, successfully removed message from the queue.");
		} catch (IOException e) {
			throw Throwables.propagate(e);
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

	
	
	
	private final class IronMqMessageTransactionSynchronization extends TransactionSynchronizationAdapter {
		private String messageId;
		private Queue queue;

		IronMqMessageTransactionSynchronization(Queue queue, io.iron.ironmq.Message msg) {
			Assert.notNull(msg, "Message should not be null");
			Assert.notNull(queue, "Queue should not be null");
			this.messageId = msg.getId();
			this.queue = queue;
		}

		@Override
		public void afterCommit() {
			try {
				logger.debug("Transaction Committed, deleting message {}", this.messageId);
				queue.deleteMessage(this.messageId);
				logger.debug("Transaction Committed, deleted message {} successfully", this.messageId);
			} catch (IOException e) {
				throw Throwables.propagate(e);
			}
		}

		@Override
		protected void finalize() throws Throwable {
			logger.trace("Finalizing " + this);
			this.queue = null;
			this.messageId = null;
			super.finalize();
		}
		
		
	}
}
