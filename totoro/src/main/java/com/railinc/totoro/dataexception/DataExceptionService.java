package com.railinc.totoro.dataexception;

import org.springframework.integration.Message;

import com.railinc.totoro.domain.DataException;

public interface DataExceptionService {
	void updateResponsiblePerson(Message<DataException> dataExceptionMessage);
	void updateResponsiblePerson(DataException dataException);
	void createTasks();
}
