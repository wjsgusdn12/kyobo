package com.mysite.kyobo.bookorder;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.mysite.kyobo.bookorderdetail.BookOrderDetail;
import com.mysite.kyobo.member.Member;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name="book_order")
public class BookOrder {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_order_idx")
	@SequenceGenerator(
	       name = "seq_order_idx",          
	       sequenceName = "seq_orderidx",   
	       allocationSize = 1
	   )
	private Integer orderIdx;
	
	@ManyToOne
	private Member member;
	
	
	private Integer totalPrice;
	
	private LocalDateTime orderDate;
	
	private String request;
	
	@OneToMany(mappedBy = "bookOrder", cascade = CascadeType.ALL)
	private List<BookOrderDetail> bookOrderDetailList = new ArrayList<>();
	
	

}
