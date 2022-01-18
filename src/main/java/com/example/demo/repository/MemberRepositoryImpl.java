package com.example.demo.repository;

import java.util.List;

import javax.persistence.EntityManager;

import com.example.demo.entity.Member;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class MemberRepositoryImpl implements MemberRepositoryCustom{ //¿Ã∏ß¿ª ∏¬√Á¡‡æﬂ «‘  MemberRepository + Impl

	private final EntityManager em;
	
	@Override
	public List<Member> findMemberCustom() {
		return em.createQuery("select m from Member m").getResultList();
	}

}
