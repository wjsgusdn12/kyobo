package com.mysite.kyobo.book;

import java.time.LocalDateTime;

import com.mysite.kyobo.author.Author;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity 
public class Book {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_book_idx")
	@SequenceGenerator(
		name = "seq_book_idx",
		sequenceName = "seq_book_idx",
		allocationSize = 1
	)
	private int bookIdx;
	
	private String bookName;
	
	private int price;
	
	private String thumbnail;
	
	private String introductionImg;
	
	@ManyToOne
    @JoinColumn(name = "author_idx")
	private Author author;
	
	private String publisher;
	
	private LocalDateTime publicationDate;
	
	private String isbn;
	
	@Lob
	@Column(name = "CONTENT")
	private String introduction;
	
}
