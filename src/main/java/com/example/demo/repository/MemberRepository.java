package com.example.demo.repository;

import java.util.List;

import javax.persistence.LockModeType;
import javax.persistence.QueryHint;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.data.repository.query.Param;

import com.example.demo.dto.MemberDto;
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
public interface MemberRepository extends JpaRepository<Member, Long>, MemberRepositoryCustom {

	//메소드 이름으로 쿼리 생성, 메소드이름이 길어질수 있다.
	List<Member> findByUsernameAndAgeGreaterThan(String username, int age); 
	
	//namedQuery 사용
//	@Query(name = "Member.findByUsername")
//	List<Member> findByUsername(@Param("username")String username);
	
	//application 실행시점에 컴파일
	@Query("select m from Member m where m.username = :username and m.age = :age")
	List<Member> findUser(@Param("username")String username,@Param("age")int age);

	@Query("select m.username from Member m")
	List<String> findUsernameList();
	
//	@Query("select new studt.datajpa.dto.MemberDto(m.id, m.username,team.name)") //dto로 조회하는 법
//	List<MemberDto> findMemberDto();
	
	@Query("select m from Member m where m.username in :names")
	List<Member> findByNames(@Param("names")List<String> names);
	
	List<Member> findListByUsername(String username); // 컬렉션, 결과 없으면 빈 리스트
	Member findMemberByUsername(String username); //단건, 결과 없으면 null
	
	@Query(value = "select m from Member m left join m.team t",
			countQuery = "select count(m.username) from Member m")//count조회 쿼리 직접 작성 가능
	Page<Member> findByAge(int age, Pageable pageable);
	
	
	@Modifying(clearAutomatically = true)// 자동 clear
	@Query("update Member m set m.age = m.age +1 where m.age >= :age")
	int bulkAgePlus(@Param("age")int age);
	
	
	
	@EntityGraph(attributePaths = {"team"}) //내부적으로 패치조인 됨
	List<Member> findGraghBy() ;
	
	@QueryHints(value = @QueryHint(name = "org.hibernate.readOnly",value = "true"))
	Member findReadOnlyByUsername(String username);
	
	@Lock(LockModeType.PESSIMISTIC_WRITE)
	List<Member> findLockByUsername(String username);
	
	@Query(value = "select * from member",nativeQuery = true)
	List<Member> findByNaticeQuery();
	//로딩시점에 문법확인 불가
	//동적쿼리 사용 불가능
	
	@Query(value = "select m.member_id as id, m.username, t.name as teamName from Member m left join team t",
			nativeQuery = true,
			countQuery = "select count(*) from member")
	Page<MemberProjection> findByNativeProjection(Pageable pageable);
}
