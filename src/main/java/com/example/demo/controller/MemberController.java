package com.example.demo.controller;

import javax.annotation.PostConstruct;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.MemberDto;
import com.example.demo.entity.Member;
import com.example.demo.repository.MemberRepository;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class MemberController {
	
	private final MemberRepository memberRepository;
	
	
	@GetMapping("/members/{id}")
	public String findMember(@PathVariable("id") Long id) {
		Member findMember = memberRepository.findById(id).get();
		return findMember.getUsername();
	}
	
	@GetMapping("/members2/{id}")
	public String findMember2(@PathVariable("id") Member member) {
		//도메인 클래스 컨버터가 작동해서 id로 회원 엔티티를 반환
		// 리파지토리를 사용해서 엔티티를 찾음
		//트랜젝션 범위 밖에서 사용하므로 조회용
		return member.getUsername();
	}
	
	@GetMapping("/members")
	public Page<MemberDto> list(Pageable pageable){
		//yml파일에서 page를 세팅하거나
		//@pageableDefult를 사용하여 페이지의 기본값을 설정가능
		Page<Member> page = memberRepository.findAll(pageable);
		Page<MemberDto> map = page.map(member -> new MemberDto(member.getId(),member.getUsername(),member.getAge(),null));
		return map;
	}
	
	@PostConstruct
	public void init() {
		for (int i = 0; i < 100; i++) {
			
			memberRepository.save(new Member("User"+i,i));
		}
	}

}
