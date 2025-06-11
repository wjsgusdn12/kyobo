package com.mysite.kyobo;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.mysite.kyobo.author.Author;
import com.mysite.kyobo.author.AuthorRepository;
import com.mysite.kyobo.book.Book;
import com.mysite.kyobo.book.BookRepository;
import com.mysite.kyobo.member.MemberRepository;
import com.mysite.kyobo.review.ReviewRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@SpringBootTest
class KyoboApplicationTests {

	private final MemberRepository member;
	private final BookRepository book;
	private final AuthorRepository author;
	private final ReviewRepository review;
	
	@Test
	void contextLoads() {
		List<Book> bookList = book.findAll();
		for(Book bookItem : bookList) {
				Author authorEntity = bookItem.getAuthor();
				
				if(authorEntity != null) {
					System.out.println("책 이름 : " + bookItem.getBookName());
					System.out.println("작가 이름 : " + authorEntity.getAuthorName());
					System.out.println("출판일자 : " + bookItem.getPublicationDate());
				} else {
					System.out.println("책을 찾을 수 없습니다");
				}
			
		}
		
	}

}
