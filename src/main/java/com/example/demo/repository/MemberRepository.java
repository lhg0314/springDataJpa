package com.example.demo.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.demo.dto.MemberDto;
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

	//�޼ҵ� �̸����� ���� ����, �޼ҵ��̸��� ������� �ִ�.
	List<Member> findByUsernameAndAgeGreaterThan(String username, int age); 
	
	//namedQuery ���
//	@Query(name = "Member.findByUsername")
//	List<Member> findByUsername(@Param("username")String username);
	
	//application ��������� ������
	@Query("select m from Member m where m.username = :username and m.age = :age")
	List<Member> findUser(@Param("username")String username,@Param("age")int age);

	@Query("select m.username from Member m")
	List<String> findUsernameList();
	
//	@Query("select new studt.datajpa.dto.MemberDto(m.id, m.username,team.name)") //dto�� ��ȸ�ϴ� ��
//	List<MemberDto> findMemberDto();
	
	@Query("select m from Member m where m.username in :names")
	List<Member> findByNames(@Param("names")List<String> names);
	
	List<Member> findListByUsername(String username); // �÷���, ��� ������ �� ����Ʈ
	Member findMemberByUsername(String username); //�ܰ�, ��� ������ null
	
	@Query(value = "select m from Member m left join m.team t",
			countQuery = "select count(m.username) from Member m")//count��ȸ ���� ���� �ۼ� ����
	Page<Member> findByAge(int age, Pageable pageable);
	
	
	@Modifying(clearAutomatically = true)// �ڵ� clear
	@Query("update Member m set m.age = m.age +1 where m.age >= :age")
	int bulkAgePlus(@Param("age")int age);
	
	
	
	@EntityGraph(attributePaths = {"team"}) //���������� ��ġ���� ��
	List<Member> findGraghBy() ;
}
