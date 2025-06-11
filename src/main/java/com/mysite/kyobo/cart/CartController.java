package com.mysite.kyobo.cart;

import java.security.Principal;
import java.util.Optional;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mysite.kyobo.member.Member;
import com.mysite.kyobo.member.MemberService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class CartController {
	private final CartService cartService;
	private final MemberService memberService;
	
	@PostMapping("/cart/insert")
	@ResponseBody
	public void inserCart(@RequestParam("isbn")String isbn, Principal principal) {
		 System.out.println("principal.getName() = " + principal.getName());
		this.cartService.insertCart(principal.getName(), isbn);
	}
	
	//특정 사용자의 카트의 수량 체크 
	@PostMapping("/cart/count")
	@ResponseBody
	public Integer checkCount(Principal principal) {
		return this.cartService.getItemCount(principal);
	}
}
