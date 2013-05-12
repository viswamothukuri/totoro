package com.railinc.totoro.domain;

public enum TaskDisposition {

	/**
	 * Available to be assigned, this is the initial state
	 */
	Available, 
	/**
	 * The task has been claimed by a user
	 */
	Assigned, 
	/**
	 * The task is closed, this could be closed due to acceptance, or timeout, or whatever
	 */
	Closed,
	
	/**
	 * the task was delegated to someone else.
	 */
	Delegated
	;
}
