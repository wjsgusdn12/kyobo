package com.mysite.kyobo.detail;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

import com.mysite.kyobo.review.Review;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OrderBy;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name="DETAIL")
public class Detail {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_book_idx")
	@SequenceGenerator(
	       name = "seq_book_idx",          
	       sequenceName = "seq_book_idx",   
	       allocationSize = 1
	   )
	private Integer bookIdx;
	
	@Column(length=200)
	private String bookName;
	
	private Integer price;
	
	@Column(length=200)
	private String thumbnail;
	
	@Column(length=200)
	private String introductionImg;
	
	@Column(length=200)
	private String publisher;
	
	@Column(length=100)
	private String isbn;
	
	@OneToMany(mappedBy="detail" , cascade=CascadeType.REMOVE, fetch = FetchType.LAZY)
	@OrderBy("createDate DESC")
	private Set<Review> reviewList;
	
	@Lob
	@Column(columnDefinition = "CLOB")
	private String bookReview;
}
