package com.example.demo;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.entity.Member;
import com.example.demo.repository.MemberRepository;

@SpringBootTest
@Transactional
@Rollback(false)
public class TestMemberRepo {
	
	@Autowired MemberRepository mr;
	
	@Test
	public void testMember() {
		Member member = new Member();
		member.setUsername("memberA");
		member.setAge(10);
		
		Member member2 = new Member();
		member2.setUsername("memberA");
		member2.setAge(20);
		
		mr.save(member);
		mr.save(member2);
		
		List<Member> members = mr.findUser("memberA",20);
		List<String> names = mr.findUsernameList();
		
		assertThat(members.get(0).getAge()).isEqualTo(20);
	}
	
	
	@Test
	public void testPage() {
		Member member = new Member("member1",10);
		Member member2 = new Member("member2",10);
		Member member3 = new Member("member3",10);
		Member member4 = new Member("member4",10);
		Member member5 = new Member("member5",10);
		
		int age = 10;
		
		mr.save(member);
		mr.save(member2);
		mr.save(member3);
		mr.save(member4);
		mr.save(member5);
		
		PageRequest request = PageRequest.of(0, 3,Sort.by(Sort.Direction.DESC,"username"));
		
		
		Page<Member> page = mr.findByAge(age, request);
		List<Member> content = page.getContent();
		Long totalElements = page.getTotalElements();
		
		for(Member m :content) {
			System.out.println(m);
		}
		
		System.out.println(totalElements);
		
	}

}
