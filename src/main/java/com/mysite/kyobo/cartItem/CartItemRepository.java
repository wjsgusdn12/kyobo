package com.mysite.kyobo.cartItem;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mysite.kyobo.cart.Cart;

public interface CartItemRepository extends JpaRepository<CartItem, Integer>{
	List<CartItem> findByCart(Cart cart);

	

}
