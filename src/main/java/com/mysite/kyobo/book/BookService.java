package com.mysite.kyobo.book;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.stereotype.Service;

import com.mysite.kyobo.author.Author;
import com.mysite.kyobo.review.ReviewRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BookService {
    private final SecurityFilterChain fiterChain;
	private final BookRepository bookRepository;
	private final ReviewRepository reviewRepository;

	public List<Book> getAllBook(){
		List<Book> bookList = bookRepository.findAll();
		return bookList;
	}
	
	public Page<BookDto> getList(int page) {
	    int size = 5; // 한 페이지당 항목 수
	    int startRow = page * size;
	    int endRow = startRow + size;

	    List<Object[]> result = bookRepository.findWithPaging(startRow, endRow);
	    long totalElements = bookRepository.countTotalBooks();

	    List<BookDto> dtoList = result.stream().map(objects -> {
	        BookDto dto = new BookDto();

	        dto.setBookIdx(((Number) objects[0]).intValue()); // book_idx
	        dto.setBookName((String) objects[1]);
	        dto.setPrice(((Number) objects[2]).intValue());
	        dto.setThumbnail((String) objects[3]);
	        dto.setIntroductionImg((String) objects[4]);

	        Author author = new Author();
	        author.setAuthorIdx(((Number) objects[5]).intValue());
	        author.setAuthorName((String) objects[6]);
	        dto.setAuthor(author);

	        dto.setPublisher((String) objects[7]);
	        dto.setPublicationDate(((Timestamp) objects[8]).toLocalDateTime());
	        dto.setIsbn((String) objects[9]);
	        dto.setAvgScore(((Number) objects[10]).doubleValue());
	        dto.setReviewCount(((Number) objects[11]).longValue());
	        dto.setIntroduction((String) objects[12]);

	        return dto;
	    }).toList();

	    // 전체 개수는 따로 구해야 함 (필요 시 count 쿼리 추가 필요)
	    return new PageImpl<>(dtoList, PageRequest.of(page, size), totalElements);
	}
	
	//검색기능
	public List<Object[]> searchBooks(String searchKeyword, int startRow, int endRow) {
        return bookRepository.searchBooksWithPaging(searchKeyword!=""?searchKeyword:"", startRow, endRow);
    }
	
	public Page<BookDto> searchBooksPage(String keyword, int page, int pageSize) {
		if (page < 0) page = 0;
		int startRow = page * pageSize;
	    List<Object[]> results = bookRepository.searchBooksWithPaging(keyword, startRow, startRow + pageSize);

	    List<BookDto> books = new ArrayList<>();
	    for (Object[] row : results) {
	        BookDto dto = new BookDto();

	        dto.setBookIdx(((Number) row[0]).intValue());                 // book_idx
	        dto.setBookName((String) row[1]);                             // book_name
	        dto.setPrice(((Number) row[2]).intValue());                   // price
	        dto.setThumbnail((String) row[3]);                            // thumbnail
	        dto.setIntroductionImg((String) row[4]);                      // introduction_img

	        Author author = new Author();
	        author.setAuthorIdx(((Number) row[5]).intValue());            // author_idx
	        author.setAuthorName((String) row[6]);                        // author_name
	        dto.setAuthor(author);

	        dto.setPublisher((String) row[7]);                            // publisher

	        if (row[8] instanceof java.sql.Timestamp) {
	            dto.setPublicationDate(((java.sql.Timestamp) row[8]).toLocalDateTime());
	        } else if (row[8] instanceof LocalDateTime) {
	            dto.setPublicationDate((LocalDateTime) row[8]);
	        }

	        dto.setIsbn((String) row[9]);                                 // isbn
	        dto.setAvgScore(row[10] != null ? ((Number) row[10]).doubleValue() : 0.0);
	        dto.setReviewCount(row[11] != null ? ((Number) row[11]).longValue() : 0L);
	        dto.setIntroduction((String) row[12]);

	        books.add(dto);
	    }

	    int totalCount = bookRepository.countSearchBooks(keyword); // 총 개수 가져오기
	    return new PageImpl<>(books, PageRequest.of(page, pageSize), totalCount);
	}

    public int countTotalBooks(String kw) {
        return bookRepository.countTotalBooksByKeyword(kw);
    }
    
    public int countTotalBooks() {
        return (int)bookRepository.countTotalBooks();
    }
    
    public int countSearchBooks(String kw) {
    	return bookRepository.countSearchBooks(kw);
    }
}
