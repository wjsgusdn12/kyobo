package com.mysite.kyobo.book;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends JpaRepository<Book, Integer> {
	Optional<Book> findById(int id);
	
	@Query(value = "SELECT * FROM ( " +
	        " SELECT inner_query.*, ROWNUM AS rn FROM ( " +
	        "  SELECT b.book_idx, b.book_name, b.price, b.thumbnail, b.introduction_img, " +
	        "         a.author_idx, a.author_name, b.publisher, b.publication_date, b.isbn, " +
	        "         COALESCE(AVG(r.score), 0) AS avg_score, COUNT(r.review_idx) AS review_count, b.introduction " +
	        "  FROM book b " +
	        "  LEFT JOIN review r ON b.book_idx = r.detail_book_idx " +
	        "  JOIN author a ON a.author_idx = b.author_idx " +
	        "  GROUP BY b.book_idx, a.author_idx, a.author_name, b.book_name, b.introduction_img, " +
	        "           b.isbn, b.price, b.publication_date, b.publisher, b.thumbnail, introduction " +
	        "  ORDER BY b.book_idx " +
	        ") inner_query WHERE ROWNUM <= :endRow ) " +
	        "WHERE rn > :startRow",
	        nativeQuery = true)
	List<Object[]> findWithPaging(@Param("startRow") int startRow, @Param("endRow") int endRow);
	
	@Query(value = "SELECT COUNT(*) FROM book b", nativeQuery = true)
	long countTotalBooks();
	
	@Query(value = "SELECT * FROM ( " +
	        " SELECT inner_query.*, ROWNUM AS rn FROM ( " +
	        "  SELECT b.book_idx, b.book_name, b.price, b.thumbnail, b.introduction_img, " +
	        "         a.author_idx, a.author_name, b.publisher, b.publication_date, b.isbn, " +
	        "         COALESCE(AVG(r.score), 0) AS avg_score, COUNT(r.review_idx) AS review_count, b.introduction " +
	        "  FROM book b " +
	        "  LEFT JOIN review r ON b.book_idx = r.detail_book_idx " +
	        "  JOIN author a ON a.author_idx = b.author_idx " +
	        "  WHERE b.book_name LIKE %:kw% " +
	        "     OR a.author_name LIKE %:kw% " +
	        "     OR b.publisher LIKE %:kw% " +
	        "  GROUP BY b.book_idx, a.author_idx, a.author_name, b.book_name, b.introduction_img, " +
	        "           b.isbn, b.price, b.publication_date, b.publisher, b.thumbnail, b.introduction " +
	        "  ORDER BY b.book_idx " +
	        ") inner_query WHERE ROWNUM <= :endRow ) " +
	        "WHERE rn > :startRow",
	        nativeQuery = true)
	List<Object[]> searchBooksWithPaging(@Param("kw") String kw, @Param("startRow") int startRow, @Param("endRow") int endRow);
    
	@Query(value = "SELECT COUNT(*) FROM book b " +
            "JOIN author a ON b.author_idx = a.author_idx " +
            "WHERE (UPPER(b.book_name) LIKE UPPER(:keyword) " +
            "        OR UPPER(a.author_name) LIKE UPPER(:keyword) " +
            "        OR UPPER(b.publisher) LIKE UPPER(:keyword))", 
            nativeQuery = true)
	int countTotalBooksByKeyword(@Param("keyword") String keyword);
	
	@Query(value = "SELECT COUNT(*) FROM book b " +
	       "JOIN author a ON b.author_idx = a.author_idx " +
	       "WHERE b.book_name LIKE %:kw% " +
	       "   OR a.author_name LIKE %:kw% " +
	       "   OR b.publisher LIKE %:kw%",
	       nativeQuery = true)
	int countSearchBooks(@Param("kw") String kw);
}
