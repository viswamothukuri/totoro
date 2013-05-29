package com.railinc.totoro.domain;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;

import com.google.common.base.Optional;

@Table(name="TASK")
@Entity
public class Task implements Auditable{

	@Id
	@GeneratedValue
	@Column(name="TASK_ID")
	private Long id;
	
	@Version
    @Column(name="VERSION")
    private Integer version;

	
	@OneToOne
	@JoinColumn(name="DELEGATED_FROM_TASK_ID",nullable=true)
	Task delegatedFrom;
	
	@OneToOne
	@JoinColumn(name="DELEGATED_TO_TASK_ID",nullable=true)
	Task delegatedTo;
	
	/**
	 * The disposition of the task 
	 */
	@Basic
	@Column(name="DISPOSITION",nullable=true)
	@Enumerated(value=EnumType.STRING)
	TaskDisposition disposition = TaskDisposition.Available;
	
	/**
	 * The completion status of a task, accepted,rejected,cancelled,timedout, etc... how did this task complete?
	 */
	@Basic
	@Column(name="COMPLETION_STATUS",nullable=true)
	@Enumerated(value=EnumType.STRING)
	TaskCompletionStatus completionStatus;
	
	
	@Embedded
	@AttributeOverrides( {
        @AttributeOverride(name="type", column = @Column(name="CAND_RESP_PERSON_TYPE") ),
        @AttributeOverride(name="id", column = @Column(name="CAND_RESP_PERSON_ID") )
	} )
	private Identity candidate;
	
	/**
	 * When someone claims the task, it becomes assigned to him/her
	 */
	@Embedded
	@AttributeOverrides( {
        @AttributeOverride(name="type", column = @Column(name="ASSN_RESP_PERSON_TYPE") ),
        @AttributeOverride(name="id", column = @Column(name="ASSN_RESP_PERSON_ID") )
	} )
	private Identity assignee;
	
	
	@Temporal(TemporalType.TIMESTAMP)
	Date created;
	
	@Temporal(TemporalType.TIMESTAMP)
	Date notificationSent;
	
	@OneToMany(mappedBy="task")
	Set<DataException> exceptions;
	
	@Embedded
	private AuditData auditData = new AuditData();
	
	
	String taskView; // custom rendered view for the exception data.
	
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
	
	public Identity getCandidate() {
		return candidate;
	}
	
	public void setCandidate(Identity candidate) {
		this.candidate = candidate;
	}
	
	public void addDataException(DataException dataException){
		if(exceptions == null){
			exceptions = new HashSet<DataException>();
		}
		dataException.setTask(this);
		exceptions.add(dataException);
	}

	@Override
	public AuditData getAuditData() {
		this.auditData = Optional.fromNullable(this.auditData).or(new AuditData());
		return this.auditData;
	}
}
