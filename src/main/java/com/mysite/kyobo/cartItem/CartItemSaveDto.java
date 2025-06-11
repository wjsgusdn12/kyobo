package com.mysite.kyobo.cartItem;

import com.mysite.kyobo.detail.Detail;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CartItemSaveDto {
	private Detail detail;
	private Integer count;
}
