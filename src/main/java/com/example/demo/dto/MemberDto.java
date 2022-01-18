package com.example.demo.dto;

import lombok.Data;

@Data
public class MemberDto {
	
	private Long id;
	private String username;
	private int age;
	private String TeamName;
	
	public MemberDto(Long id, String username,int age, String teamName) {
		this.id = id;
		this.username = username;
		this.age = age;
		TeamName = teamName;
	}
	
	

}
