package com.mysite.kyobo.review;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.mysite.kyobo.detail.Detail;
import com.mysite.kyobo.detail.DetailRepository;
import com.mysite.kyobo.member.Member;
import com.mysite.kyobo.reviewcomment.ReviewComment;
import com.mysite.kyobo.reviewcomment.ReviewCommentRepository;
import com.mysite.kyobo.reviewcomment.ReviewCommentResponseDto;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ReviewService {
	private final DetailRepository detailRepository;
	private final ReviewRepository reviewRepository;
	private final ReviewCommentRepository reviewCommentRepository;

	// 리뷰 작성하기
	public void reviewCreate(String review, String isbn, Integer score, Member member) {
		Detail detail = this.detailRepository.findByisbn(isbn);
		Review r = new Review();
		r.setContent(review);
		r.setDetail(detail);
		r.setScore(score);
		r.setAuthor(member);
		r.setCreateDate(LocalDateTime.now());
		this.reviewRepository.save(r);
	}
	
	public Page<ReviewResponseDto> getReviewListWithComments(String isbn, int page, int size) {
	    int startRow = page * size;
	    int endRow = startRow + size;

	    // 리뷰 리스트 조회
	    List<Review> reviewEntities = reviewRepository.findReviewsWithPaging(isbn, startRow, endRow);
	    long totalCount = reviewRepository.countReviewsByIsbn(isbn);

	    List<ReviewResponseDto> dtoList = reviewEntities.stream().map(r -> {
	        ReviewResponseDto dto = new ReviewResponseDto(
	                r.getReviewIdx(),
	                r.getContent(),
	                r.getScore(),
	                r.getCreateDate(),
	                r.getAuthor().getId()
	        );

	        List<ReviewComment> comments = reviewCommentRepository.findByReviewIdx(r.getReviewIdx());

	        Set<ReviewCommentResponseDto> commentDtoSet = comments.stream()
	                .map(rc -> new ReviewCommentResponseDto(
	                        rc.getCommentIdx(),
	                        rc.getContent(),
	                        rc.getCreateDate(),
	                        rc.getMember().getId()
	                ))
	                .collect(Collectors.toSet());

	        dto.setCommentList(commentDtoSet);
	        return dto;
	    }).toList();

	    return new PageImpl<>(dtoList, PageRequest.of(page, size), totalCount);
	}
}