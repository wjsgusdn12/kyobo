package com.mysite.kyobo.reviewcomment;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mysite.kyobo.DataNotFoundException;
import com.mysite.kyobo.member.Member;
import com.mysite.kyobo.review.Review;
import com.mysite.kyobo.review.ReviewRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ReviewCommentService {
	private final ReviewCommentRepository commentRepository;
	private final ReviewRepository reviewRepository;

	public ReviewComment createComment(Integer reviewIdx, String content, Member meber) {
		Review review = this.reviewRepository.findById(reviewIdx)
			    .orElseThrow(() -> new DataNotFoundException("해당 리뷰를 찾을 수 없습니다."));

		ReviewComment c = new ReviewComment();
		c.setContent(content);
		c.setReview(review);
		c.setCreateDate(LocalDateTime.now());
		c.setMember(meber);
		return this.commentRepository.save(c); //save 메서드는 객체를 저장하고 반환해줌 

	}
	
	//update하는 메서드 
	public ReviewComment updateComment(Integer commentIdx, String commentData) {
		ReviewComment reviewComment = this.commentRepository.findById(commentIdx)
		.orElseThrow(()-> new DataNotFoundException("해당 리뷰를 찾을 수 없습니다."));
		
		reviewComment.setContent(commentData);
		reviewComment.setCreateDate(LocalDateTime.now());
		return this.commentRepository.save(reviewComment);
		
		
	}
	
	//삭제하는 메서드 
	public void deleteComment(Integer commentIdx) {
		ReviewComment reviewComment = this.commentRepository.findById(commentIdx)
		.orElseThrow(()-> new DataNotFoundException("해당 리뷰를 찾을 수 없습니다."));
		this.commentRepository.delete(reviewComment);
	}
	
	

}
