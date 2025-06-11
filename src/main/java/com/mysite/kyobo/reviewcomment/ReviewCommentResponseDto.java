package com.mysite.kyobo.reviewcomment;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReviewCommentResponseDto {
	public ReviewCommentResponseDto(Integer commentIdx, String comment, LocalDateTime createDate, String memberId){
		this.commentIdx=commentIdx;
		this.comment = comment;
		this.createDate = createDate;
		this.memberId = memberId;
	}
	
	
	
	private Integer commentIdx;
	
	private String memberId;
	
	private String comment;
	
	private LocalDateTime createDate;

}
