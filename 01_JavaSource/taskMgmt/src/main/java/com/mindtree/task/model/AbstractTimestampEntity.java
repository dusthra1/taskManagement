package com.mindtree.task.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@MappedSuperclass
public abstract class AbstractTimestampEntity {
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="CREATE_TS")
	@CreationTimestamp
	private Date create_ts;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="UPDATE_TS")
	@UpdateTimestamp
	private Date update_ts;
	
	@PrePersist
	protected void onCreate() {
		update_ts = create_ts = new Date();
	}

	@PreUpdate
	protected void onUpdate() {
		update_ts = new Date();
	}
	
}
