package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.Member;

/**
 * JpaRepository -> PagingAndSortingRepository -> CrudRepository -> Repository
 * 
 * 주요 메서드
 * save : persist + merge
 * delete : remove
 * getOne : 프록시 조회
 * findAll : 모든 리스트 조회 , sorting, paging기능 제공
 *
 */
public interface MemberRepository extends JpaRepository<Member, Long> {


	
	
}
