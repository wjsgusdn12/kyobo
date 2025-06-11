package com.mysite.kyobo.book;

import java.time.LocalDateTime;

import com.mysite.kyobo.author.Author;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BookDto {
	private int bookIdx;
	private String bookName;
	private int price;
	private String thumbnail;
	private String introductionImg;
	private Author author;
	private String publisher;
	private LocalDateTime publicationDate;
	private String isbn;
	
	private double avgScore;
	private long reviewCount;

	private String introduction;
}
