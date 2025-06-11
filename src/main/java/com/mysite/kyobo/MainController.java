package com.mysite.kyobo;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.mysite.kyobo.book.BookDto;
import com.mysite.kyobo.book.BookService;
import com.mysite.kyobo.cart.CartService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class MainController {
	private final BookService bookService;
	private final CartService cartService;

	@GetMapping("/")
	public String home(Model model, @RequestParam(value="page", defaultValue="0") int page,
			Principal principal) {
	    Page<BookDto> paging = this.bookService.getList(page);

	    // 현재 페이지 번호, 전체 페이지 수
	    int currentPage = paging.getNumber();
	    int totalPages = paging.getTotalPages();

	    int pageGroupSize = 10; // 한 번에 보여줄 페이지 번호 개수
	    int startPage = (currentPage / pageGroupSize) * pageGroupSize;
	    int endPage = Math.min(startPage + pageGroupSize - 1, totalPages - 1);

	    List<Integer> pageNumbers = new ArrayList<>();
	    for (int i = startPage; i <= endPage; i++) {
	        pageNumbers.add(i);
	    }
	    
	    if(principal!=null) {
			int count = this.cartService.getItemCount(principal);
			System.out.println("로그인 한 사용자의 장바구니 갯수 :" + count);
			model.addAttribute("countCart", count);
		}

	    model.addAttribute("paging", paging);
	    model.addAttribute("pageNumbers", pageNumbers);

	    return "main_list";
	}
	
	@GetMapping("/cart")
	public String cart() {
		return "cart";
	}
	
	@GetMapping("/login")
	public String login() {
		return "login";
	}

	@GetMapping("/logout")
	public String logout() {
		return "logout";
	}
	
	@GetMapping("/find_account")
	public String findAccount() {
		return "find_account";
	}
	
	@GetMapping("/my_page_lock")
	public String myPageLock() {
		return "my_page_lock";
	}
}