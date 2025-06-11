package com.mysite.kyobo.reviewcomment;

import java.time.LocalDateTime;

import com.mysite.kyobo.member.Member;
import com.mysite.kyobo.review.Review;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name="REVIEWCOMMENT")
public class ReviewComment {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_reviewcomment_idx")
	@SequenceGenerator(
	       name = "seq_reviewcomment_idx",          
	       sequenceName = "seq_reviewcomment_idx",   
	       allocationSize = 1
	   )
	private Integer commentIdx;
	
	@ManyToOne
	private Review review;
	
	@ManyToOne
	private Member member;
	
	private String content;
	
	private LocalDateTime createDate;

}
