package com.example.demo;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

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
	@PersistenceContext EntityManager em;
	
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
	
	@Test
	public void testBulk() throws InterruptedException {
		
		Member member = new Member("member1",10);
		Member member2 = new Member("member2",15);
		Member member3 = new Member("member3",20);
		Member member4 = new Member("member4",30);
		Member member5 = new Member("member5",40);
		
		mr.save(member);
		mr.save(member2);
		mr.save(member3);
		mr.save(member4);
		mr.save(member5);
		
		
		mr.bulkAgePlus(20);
		
//		em.flush(); //영속성 컨텍스트때문에 flush해줘야 변경된 데이터 받을수 있음
//		em.clear();
		List<Member> findMems = mr.findGraghBy();
		
		System.out.println(findMems.get(0).getTeam().getName());
	}
	
	
	@Test
	public void testQueryHint() {
		Member member1 = mr.save(new Member("member1",10));
		em.flush();
		em.clear(); //1차캐시 날리기 
		
		Member findMember = mr.findReadOnlyByUsername("member1");
		
		findMember.setUsername("member2"); //readOnly true , 변경 무시
		em.flush();
	}
	
	@Test
	public void testLock() {
		Member member1 = mr.save(new Member("member1",10));
		em.flush();
		em.clear(); //1차캐시 날리기 
		
		List<Member> findMember = mr.findLockByUsername("member1");
		
	}
	
	@Test
	public void testCustom() {
		Member member1 = mr.save(new Member("member1",10));
		em.flush();
		em.clear(); //1차캐시 날리기 
		
		List<Member> findMember = mr.findMemberCustom();
		
	}
	
	@Test
	public void testAudit() throws Exception {
		Member member1 = mr.save(new Member("member1",10));
		
		//Thread.sleep(100);
		member1.setUsername("member2");
		
		em.flush();
		em.clear();
		
		Member findMember = mr.findById(member1.getId()).get();
		
		System.out.println("=================================================");

		
	}
	
	@Test
	public void dutyChekTest() {
		Member mem = mr.findById(1L).get(); //default :transaction(readOnly =true)
		
		mem.setUsername("newname");
		Member newMember = new Member("newName",20);
		mr.save(newMember);// persist or merge
		
		mr.bulkAgePlus(50);
		
	}
}
