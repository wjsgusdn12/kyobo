package com.mysite.kyobo.review;

import java.time.LocalDateTime;
import java.util.Set;

import com.mysite.kyobo.member.Member;
import com.mysite.kyobo.reviewcomment.ReviewComment;
import com.mysite.kyobo.reviewcomment.ReviewCommentResponseDto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ReviewResponseDto {
	
	public ReviewResponseDto(Integer reviewIdx, String content, Integer score, LocalDateTime createDate, 
			String memberId){
		 System.out.println("DTO 생성자 호출됨: " + reviewIdx + ", " + memberId);
		this.reviewIdx = reviewIdx;
		this.content = content;
		this.score = score;
		this.createDate = createDate;
		this.memberId= memberId;
		
	}
	
	
	private Integer reviewIdx;
	
	private LocalDateTime createDate;
	
	private String memberId;
	
	private String content;
	
	private Integer score;
	
	private Set<ReviewCommentResponseDto> commentList; 
	
	
	

}
