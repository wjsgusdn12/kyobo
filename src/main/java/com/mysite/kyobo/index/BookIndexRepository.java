package com.mysite.kyobo.index;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.mysite.kyobo.detail.Detail;

public interface BookIndexRepository extends JpaRepository<BookIndex, Integer>{
	@Query("SELECT b FROM BookIndex b WHERE b.detail = :detail ORDER BY b.indexIdx ASC")
	List<BookIndex> findByDetail(@Param("detail") Detail detail);

	
	

}
