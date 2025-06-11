package com.mysite.kyobo.bookorder;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BookOrderResponseDto {
	private String bookName;
	
	private String thumbnail;
	
	private Integer count;
	
	private  Integer price;
	
	public BookOrderResponseDto(String bookName, String thumbnail, Integer count, Integer price) {
		this.bookName= bookName;
		this.thumbnail = thumbnail;
		this.count = count;
		this.price = price;
	}
	
	public BookOrderResponseDto() {
		
	}

}
