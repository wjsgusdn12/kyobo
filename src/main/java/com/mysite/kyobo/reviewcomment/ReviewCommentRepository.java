package com.mysite.kyobo.reviewcomment;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ReviewCommentRepository  extends JpaRepository<ReviewComment, Integer>{
	@Query("SELECT rc FROM ReviewComment rc JOIN rc.member m WHERE rc.review.reviewIdx = :reviewIdx ORDER BY rc.createDate DESC")
	List<ReviewComment> findByReviewIdx(@Param("reviewIdx") Integer reviewIdx);
}
