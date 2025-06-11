package com.mysite.kyobo.reviewcomment;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReviewCommentItemDto {
	
	
	public ReviewCommentItemDto() {
		
	}
	
	
	private String writer;
	
	private String content;
	
	private String date;
	
	private Integer commentIdx;


}
