package com.example.demo.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

@MappedSuperclass
public class BaseJpaEntity {
	
	@Column(updatable = false)
	private LocalDateTime createDate;
	private LocalDateTime updateDate;
	
	@PrePersist
	public void preCreate() {
		LocalDateTime now = LocalDateTime.now();
		createDate = now;
		updateDate = now;
	}
	
	@PreUpdate
	public void preUpdate() {
		LocalDateTime now = LocalDateTime.now();
		updateDate = now;
	}

}
