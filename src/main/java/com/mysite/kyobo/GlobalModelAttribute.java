package com.mysite.kyobo;

import java.security.Principal;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.mysite.kyobo.cart.CartService;

import lombok.RequiredArgsConstructor;

@ControllerAdvice
@RequiredArgsConstructor
public class GlobalModelAttribute {
	private final CartService cartService;

	@ModelAttribute
	public void addCartCountToModel(Principal principal, Model model) {
		if (principal != null) {
			int count = cartService.getItemCount(principal);
			System.out.println("전역 모델에 장바구니 수량 추가됨: " + count);
			model.addAttribute("countCart", count);
		}
	}
}
