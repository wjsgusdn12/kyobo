package com.mysite.kyobo.detail;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.mysite.kyobo.review.ReviewResponseDto;

import oracle.jdbc.proxy.annotation.Post;

public interface DetailRepository extends JpaRepository<Detail, Integer>{
	Detail findBybookName(String bookName);
	Detail findByisbn(String isbn);
	
	@Query("SELECT DISTINCT d FROM Detail d " +
		       "LEFT JOIN FETCH d.reviewList r " +
		       "LEFT JOIN FETCH r.commentList " +
		       "WHERE d.isbn = :postIsbn")
	Optional<Detail> findWithReviewsAndCommentsByIsbn(@Param("postIsbn") String postIsbn);
	
	
	
	

}
