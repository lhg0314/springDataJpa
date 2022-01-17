package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.Member;

/**
 * JpaRepository -> PagingAndSortingRepository -> CrudRepository -> Repository
 * 
 * �ֿ� �޼���
 * save : persist + merge
 * delete : remove
 * getOne : ���Ͻ� ��ȸ
 * findAll : ��� ����Ʈ ��ȸ , sorting, paging��� ����
 *
 */
public interface MemberRepository extends JpaRepository<Member, Long> {


	
	
}
