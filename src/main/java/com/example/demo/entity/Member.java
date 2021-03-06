package com.example.demo.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@ToString(of = {"id","username","age"})
@NoArgsConstructor
/*
 * application실행 시 에러를 잡아줌
 * */
//@NamedQuery(
//		name = "Member.findByUsername",
//		query = "select m from Member m where m.username = :username"
//)


public class Member{
	
	@Id
	@GeneratedValue
	@Column(name = "member_id")
	private Long id;
	private String username;
	private int age;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "team_id")
	private Team team;
	
	

	public void chageTeam(Team team) {
		this.team = team;
		team.getMembers().add(this);
	}



	public Member(String username, int age) {
		this.username = username;
		this.age = age;
	}



	public Member(String username, int age, Team team) {
		this.username = username;
		this.age = age;
		this.team = team;
	}
	
	

}
