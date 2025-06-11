package com.mysite.kyobo.cart;

import java.util.ArrayList;
import java.util.List;

import com.mysite.kyobo.cartItem.CartItem;
import com.mysite.kyobo.detail.Detail;
import com.mysite.kyobo.member.Member;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Setter
@Getter
@Table(name = "cart")
public class Cart {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_cart_idx")
	@SequenceGenerator(
	       name = "seq_cart_idx",          
	       sequenceName = "seq_cartidx",   
	       allocationSize = 1
	   )
	private Integer cartIdx;

	@OneToOne // 한 명당 하나의 장바구니
	private Member member;

	@OneToMany(mappedBy = "cart", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<CartItem> items = new ArrayList<>();
}
