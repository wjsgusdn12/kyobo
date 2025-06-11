package com.mysite.kyobo.review;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Integer> {
	//@Query("SELECT COUNT(*), AVG(r.score) FROM Review r WHERE r.book.bookIdx = :bookIdx")
	//Object findReviewStatisticsByBookId(@Param("bookIdx") int bookIdx);
	@Query(value = "SELECT * FROM ( " +
	        "  SELECT inner_query.*, ROWNUM AS rn FROM ( " +
	        "    SELECT r.* FROM review r " +
	        "    JOIN detail d ON d.book_idx = r.detail_book_idx " +
	        "    JOIN member m ON m.member_idx = r.author_member_idx " +
	        "    WHERE d.isbn = :isbn " +
	        "    ORDER BY r.create_date DESC " +
	        "  ) inner_query WHERE ROWNUM <= :endRow " +
	        ") WHERE rn > :startRow",
	        nativeQuery = true)
	List<Review> findReviewsWithPaging(@Param("isbn") String isbn,
	                                   @Param("startRow") int startRow,
	                                   @Param("endRow") int endRow);

	@Query("SELECT COUNT(r) FROM Review r WHERE r.detail.isbn = :isbn")
	long countReviewsByIsbn(@Param("isbn") String isbn);
}
