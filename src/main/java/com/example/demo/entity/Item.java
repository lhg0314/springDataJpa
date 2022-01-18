package com.example.demo.entity;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.domain.Persistable;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@EntityListeners(AuditingEntityListener.class)
public class Item implements Persistable<String>{ //
	
	@Id
	@GeneratedValue
	private String id;
	
	@CreatedDate
	private LocalDateTime createdDate; //persist전에 값세팅

	@Override
	public String getId() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isNew() {
		// 새로운 객체여부 구별
		return createdDate == null;
	}

}
