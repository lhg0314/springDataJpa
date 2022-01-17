package com.example.demo;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
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
		
		Member savedMem = mr.save(member);
		
		Member findMem = mr.findById(savedMem.getId()).get();
		Long count = mr.count();
		
		assertThat(count).isEqualTo(1);
		assertThat(findMem.getId()).isEqualTo(savedMem.getId());
		
		mr.delete(findMem);
	}

}
