package com.mysite.kyobo.bookorder;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BookOrderListDto {
	private String bookName;
	private String thumbnail;
	private Integer totalPrice;
	private String orderDate;
	private Integer totalCount;
	
	public BookOrderListDto(Integer totalPrice, String orderDate, String bookName, String thumbnail, Integer totalCount) {
		this.totalPrice= totalPrice;
		this.orderDate= orderDate;
		this.bookName=bookName;
		this.thumbnail=thumbnail;
		this.totalCount=totalCount;
	}
}
