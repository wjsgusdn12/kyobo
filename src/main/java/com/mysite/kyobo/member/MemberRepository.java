package com.mysite.kyobo.member;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberRepository extends JpaRepository<Member, Integer> {
	Optional<Member> findById(String id);
	Optional<Member> findByEmail(String email);
    Optional<Member> findByPhone(String phone);
    @Query(value = "select case when count(*) > 0 then 1 else 0 end from member where id = :id and ROWNUM = 1", nativeQuery = true)
    int existsByIdCustom(@Param("id") String id);
    @Query(value = "select case when count(*) > 0 then 1 else 0 end from member where email = :email and ROWNUM = 1", nativeQuery = true)
    int existsByEmailCustom(@Param("email") String email);
    @Query(value = "select case when count(*) > 0 then 1 else 0 end from member where phone = :phone and ROWNUM = 1", nativeQuery = true)
    int existsByPhoneCustom(@Param("phone") String phone);
    
	Member findMemberById(String id);
	Member findIdByNameAndPhone(String name, String phone);
	Member findByIdAndNameAndPhone(String id, String name, String phone);
	boolean existsById(String id);
    boolean existsByEmail(String email);
    boolean existsByPhone(String phone);
    
}
