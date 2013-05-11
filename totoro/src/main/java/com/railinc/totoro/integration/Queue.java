package com.railinc.totoro.integration;

public interface Queue {
	/**
	 * enqueues the string message (usually async) and returns
	 * @param m
	 */
	void sendMessage(String m);
}
