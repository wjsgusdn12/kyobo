package com.mysite.kyobo.cart;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.mysite.kyobo.cartItem.CartItem;
import com.mysite.kyobo.cartItem.CartItemRepository;
import com.mysite.kyobo.detail.Detail;
import com.mysite.kyobo.detail.DetailRepository;
import com.mysite.kyobo.member.Member;
import com.mysite.kyobo.member.MemberRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CartService {
	private final CartRepository cartRepository;
	private final DetailRepository detailRepository;
	private final CartItemRepository cartItemRepository;
	private final MemberRepository memberRepository;
	
	//장바구니 추가
	public Cart insertCart(String id, String isbn) {
		Member member = this.memberRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 회원이 없습니다."));

		Detail detail = this.detailRepository.findByisbn(isbn);
		if (detail == null) {
		    throw new IllegalArgumentException("해당 도서가 없습니다.");
		}
	
		Cart cart = this.cartRepository.findBymember(member).orElseGet(()->{
			Cart newCart = new Cart();
			newCart.setMember(member);
			return newCart;
		});
		
		// ✅ 같은 책이 이미 있는지 확인
	    Optional<CartItem> existingItemOpt = cart.getItems().stream()
	        .filter(item -> item.getDetail().getIsbn().equals(isbn))
	        .findFirst();

	    if (existingItemOpt.isPresent()) {
	        // 같은 책이 있으면 수량 증가
	        CartItem existingItem = existingItemOpt.get();
	        existingItem.setCount(existingItem.getCount() + 1);
	    } else {
	        // 없으면 새로 추가
	        CartItem newItem = new CartItem();
	        newItem.setCart(cart);
	        newItem.setCount(1);
	        newItem.setDetail(detail);
	        cart.getItems().add(newItem);
	    }
		
		return this.cartRepository.save(cart);
		
	}
	
	//사용자를 통해 장바구니 갯수 확인 
	public int getItemCount(Principal principal) { 
		//principal 을통해 cart idx 를 알기 
		//cart idx를 통해 총 count 객수 찾아오기 
		Member member = this.memberRepository.findById(principal.getName())
				.orElseThrow(()-> new IllegalArgumentException("해당 회원이 없습니다."));
		Optional<Cart> cartOpt = this.cartRepository.findBymember(member);
		Cart cart = null;
		if(cartOpt.isPresent()) {
			cart = cartOpt.get();
		}else {
			return -1; //장바구니 담긴 책이 없을때 처리 
		}
		
		// 3. 장바구니에 있는 아이템들 가져오기
	    List<CartItem> items = this.cartItemRepository.findByCart(cart);

	    // 4. 전체 수량 계산
	    int totalCount = items.stream()
	            .mapToInt(CartItem::getCount)
	            .sum();

	    return totalCount;
		
	
		
	}

}
