package com.mysite.kyobo.bookorder;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.mysite.kyobo.member.Member;

public interface BookOrderRepository extends JpaRepository<BookOrder, Integer>{
	@EntityGraph(attributePaths = {"bookOrderDetailList"})
	public Optional<BookOrder> findByOrderIdx(Integer orderIdx);
	public List<BookOrder> findBymember(Member member);
}
