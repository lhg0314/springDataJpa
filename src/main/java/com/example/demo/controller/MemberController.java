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
		//������ Ŭ���� �����Ͱ� �۵��ؼ� id�� ȸ�� ��ƼƼ�� ��ȯ
		// �������丮�� ����ؼ� ��ƼƼ�� ã��
		//Ʈ������ ���� �ۿ��� ����ϹǷ� ��ȸ��
		return member.getUsername();
	}
	
	@GetMapping("/members")
	public Page<MemberDto> list(Pageable pageable){
		//yml���Ͽ��� page�� �����ϰų�
		//@pageableDefult�� ����Ͽ� �������� �⺻���� ��������
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
