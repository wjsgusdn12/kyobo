package com.mysite.kyobo.review;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

import com.mysite.kyobo.detail.Detail;
import com.mysite.kyobo.member.Member;
import com.mysite.kyobo.reviewcomment.ReviewComment;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OrderBy;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name="REVIEW")
public class Review {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_review_idx")
	@SequenceGenerator(
	       name = "seq_review_idx",          
	       sequenceName = "seq_review_idx",   
	       allocationSize = 1
	   )
	private Integer reviewIdx;
	
	@ManyToOne
	@JoinColumn(name = "detail_book_idx") 
	private Detail detail;
	
	private LocalDateTime createDate;
	
	@ManyToOne
	private Member author;
	
	@Column(length=4000)
	private String content;
	
	private Integer score;
	
	@OneToMany(mappedBy="review" , cascade=CascadeType.REMOVE, fetch = FetchType.LAZY)
	@OrderBy("createDate DESC")
	private Set<ReviewComment> commentList;
}