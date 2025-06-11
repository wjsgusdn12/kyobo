package com.mysite.kyobo.cart;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mysite.kyobo.member.Member;

public interface CartRepository extends JpaRepository<Cart, Integer>{
	public Optional<Cart> findBymember(Member member);
}
