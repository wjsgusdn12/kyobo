package com.mysite.kyobo.book;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class BookController {

	private final BookService bookService;
	
	@GetMapping("/book/search_result")
	@ResponseBody
    public Map<String, Object> searchBooks(
            @RequestParam(value = "kw", defaultValue = "") String kw,
            @RequestParam(value="page", defaultValue="1") int page) {
		
        // 페이지네이션 설정
        int pageSize = bookService.countTotalBooks();
        int startRow = (page - 1) * pageSize;
        int endRow = page * pageSize;
        
        // 검색 쿼리 실행
        List<Object[]> results = bookService.searchBooks(kw, startRow, endRow);
        int totalCount = bookService.countTotalBooks(kw);  // 전체 도서 수 (전체 페이지 계산용)

        // 페이지네이션 계산
        int totalPages = (int)(Math.ceil((double) totalCount / pageSize));

        // 결과 반환
        Map<String, Object> response = new HashMap<>();
        response.put("results", results);
        response.put("totalPages", totalPages);

        return response;
    }
	
	@GetMapping("/search_result")
	public String showSearchResults(@RequestParam("kw") String kw,
	                                @RequestParam(value = "page", defaultValue = "1") int page,
	                                Model model) {
	    int pageSize = 5;
	    int zeroBasedPage = page - 1 < 0 ? 0 : page - 1;
	    Page<BookDto> paging = bookService.searchBooksPage(kw, zeroBasedPage, pageSize);

	    List<Integer> pageNumbers = IntStream.rangeClosed(1, paging.getTotalPages())
	                                         .boxed().collect(Collectors.toList());

	    model.addAttribute("searchKeyword", kw);
	    model.addAttribute("paging", paging);
	    model.addAttribute("pageNumbers", pageNumbers);
	    model.addAttribute("totalElements", paging.getTotalElements());

	    return "search_result";
	}
	
}
