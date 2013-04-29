package com.railinc.totoro.domain;

import java.util.Date;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Version;

@Table(name="TASK")
@Entity
public class Task {

	@Id
	@GeneratedValue
	@Column(name="TASK_ID")
	private Long id;
	
	@Version
    @Column(name="VERSION")
    private Integer version;

	
	
	TaskDisposition disposition;
//	TaskAssignee assignee;
//	TaskAssignee delegator; // may be null
//	TaskResult result; // accepted,rejected,exempted
	
	Date created;
	Date notificationSent;
	String taskView; // custom rendered view for the exception data.
	
	@OneToMany(mappedBy="task")
	Set<DataException> exceptions;
	
	
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}
	


}
