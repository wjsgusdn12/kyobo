package com.mysite.kyobo.bookorder;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.mysite.kyobo.cart.CartService;
import com.mysite.kyobo.detail.Detail;
import com.mysite.kyobo.detail.DetailRepository;
import com.mysite.kyobo.detail.DetailService;
import com.mysite.kyobo.member.Member;
import com.mysite.kyobo.member.MemberService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class BookOrderController {
	private final MemberService memberService;
	private final CartService cartService;
	private final BookOrderService bookOrderService;
	private final DetailService detailSercie;
	private final DetailRepository detailRepository;
	
	//장바구니 이동 
	@GetMapping("/orderPayment")
	public String orderPayment(Principal principal, Model model) {
		Optional<Member> memberOpt= this.memberService.findById(principal.getName());
		Member member = null;
		if(memberOpt.isPresent()){
			member = memberOpt.get();
		}else {
			//예외 처리 
		}
		//카트의 속한 총 책의 갯수 
		int count = this.cartService.getItemCount(principal);
		//카트의 장바구니 정보 
		List<BookOrderResponseDto> orderListDto = this.bookOrderService.getOrderList(principal.getName());
		//카트 장바구니의 총 가격 
		int totalPrice = this.bookOrderService.getOrderTotalPrice(principal.getName());
		
		model.addAttribute("member", member);
		model.addAttribute("countCart", count);
		model.addAttribute("orderListDto", orderListDto);
		model.addAttribute("totalPrice", totalPrice);
		return "orderPayment";
	}
	//주문목록 저장 
	@PostMapping("/order/insert")
	public String orderInsert(
			Principal principal,
			@RequestParam("totalPrice") String totalPriceStr,
			@RequestParam("request") String request,
			Model model) {
		// ,와 원 제거하고 숫자만 남기기
		int totalPrice = Integer.parseInt(totalPriceStr.replaceAll("[^\\d]", ""));

		int bookOrder_idx = this.bookOrderService.orderInsert(principal.getName(), totalPrice, request);

		List<BookOrderResponseDto> bookOrderList = this.bookOrderService.getBookOrder(bookOrder_idx);
		Member member = this.memberService.findMemberById(principal.getName());
		int orderPrice = this.bookOrderService.getOrderCount(bookOrder_idx);
		int totalCount = this.bookOrderService.getOrderTotalCount(bookOrder_idx);

		model.addAttribute("bookOrderList", bookOrderList);
		model.addAttribute("member", member);
		model.addAttribute("orderPrice", orderPrice);
		model.addAttribute("totalCount", totalCount);

		return "order_complete";
	}
	//나의 주문목록조회
	@GetMapping("/order/index")
	public String getOrderList(Principal principal, Model model) {
		//로그인한 멤버 저장하기 
		Member member = this.memberService.findMemberById(principal.getName());
		//BookOrderListDto 조회 
		List<BookOrderListDto> bookOrderListDto = this.bookOrderService.getBookOrderListDto(principal.getName());
		
		model.addAttribute("member", member);
		model.addAttribute("bookOrderListDto", bookOrderListDto);
		return "index";
	}
	//바로 구매 페이지 띄워주기 
	@GetMapping("/order/buynow")
	public String orderBuyNow(@RequestParam("isbn")String isbn, Principal principal, Model model ) {
		//카트 데이터 넣기 .. 카트에 바로넣지 않고 바로 구매로 넘어가기 
		//bookOrder 을 넣고 
		// 1. DB에서 기존 책 정보 조회 (예: id나 title로 조회)
		Detail detail = this.detailRepository.findByisbn(isbn);
		//로그인한 멤버 저장하기 
		Member member = this.memberService.findMemberById(principal.getName());
		model.addAttribute("detail", detail);
		model.addAttribute("member", member);
		return "buyNow";
	}
	//바로구매 페이지 -> 구매하기 
	@PostMapping("/order/buynow/complete")
	public String orderBuyNowComplete(Principal principal,
			@RequestParam("totalPrice") String totalPriceStr,  // 문자열로 변경
			@RequestParam("request") String request,
			Model model,
			@RequestParam("orderIsbn") String orderIsbn) {
		// ,와 원 제거 후 숫자만 추출
		int totalPrice = Integer.parseInt(totalPriceStr.replaceAll("[^\\d]", ""));
		//바로 구매 메서드 
		int bookOrderIdx = this.bookOrderService.puyNow(principal.getName(), totalPrice, request, orderIsbn);
		//저장된 주문목록 가져오기 
		List<BookOrderResponseDto> bookOrderList= this.bookOrderService.getBookOrder(bookOrderIdx);
		//로그인한 멤버 저장하기 
		Member member = this.memberService.findMemberById(principal.getName());
		model.addAttribute("bookOrderList", bookOrderList);
		model.addAttribute("member", member);
		
		return "buyNow_complete";
	}
}
