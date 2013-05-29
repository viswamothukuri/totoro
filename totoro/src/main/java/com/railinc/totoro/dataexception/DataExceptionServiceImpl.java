package com.railinc.totoro.dataexception;

import java.util.Collection;

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.Message;

import com.railinc.totoro.domain.DataException;
import com.railinc.totoro.domain.Responsibility;
import com.railinc.totoro.domain.Task;
import com.railinc.totoro.responsibility.ResponsibilityService;

public class DataExceptionServiceImpl implements DataExceptionService {
	@Autowired
	private ResponsibilityService responsibilityService;
	
	@Autowired
	SessionFactory sessionFactory;

	@Override
	public void updateResponsiblePerson(Message<DataException> dataExceptionMessage) {
		updateResponsiblePerson(dataExceptionMessage.getPayload());
	}
	
	@Override
	public void updateResponsiblePerson(DataException dataException) {
		Responsibility responsibility = responsibilityService.getResponsibility(dataException);
		dataException.setResponsiblePerson(responsibility.getResponsiblePerson());
	}
	
	@Override
	public void createTasks(){
		Collection<DataException> dataExceptions = getDataExceptionsDontHaveTasks();
		for (DataException dataException : dataExceptions) {
			sessionFactory.getCurrentSession().save(buildTask(dataException));
		}
	}
	
	private Task buildTask(DataException dataException) {
		Task task = new Task();
		task.setCandidate(dataException.getResponsiblePerson());
		task.addDataException(dataException);
		return task;
	}

	@SuppressWarnings("unchecked")
	public Collection<DataException> getDataExceptionsDontHaveTasks(){
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(DataException.class);
		criteria.add(Restrictions.isNull(DataException.PROPERTY_TASK));
		return criteria.list();
	}
	
	public void setResponsibilityService(ResponsibilityService responsibilityService) {
		this.responsibilityService = responsibilityService;
	}

}
