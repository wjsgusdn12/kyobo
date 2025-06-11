package com.mysite.kyobo.cartItem;

import com.mysite.kyobo.cart.Cart;
import com.mysite.kyobo.detail.Detail;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name="cart_item")
public class CartItem {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_cartItem_idx")
	@SequenceGenerator(
	       name = "seq_cartItem_idx",          
	       sequenceName = "seq_cartItemidx",   
	       allocationSize = 1
	   )
	private Integer cartitemIdx;
	
	@ManyToOne
	private Cart cart;
	
	@ManyToOne
	private Detail detail;
	
	private Integer count;
}
