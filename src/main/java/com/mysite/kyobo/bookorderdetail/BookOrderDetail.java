package com.mysite.kyobo.bookorderdetail;

import com.mysite.kyobo.bookorder.BookOrder;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name="book_order_detail")
public class BookOrderDetail {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_book_order_detail_idx")
	@SequenceGenerator(
	       name = "seq_book_order_detail_idx",          
	       sequenceName = "seq_book_order_detail_idx",   
	       allocationSize = 1
	   )
    private Integer bookOrderDetailIdx;

    @ManyToOne
    @JoinColumn(name = "order_idx")
    private BookOrder bookOrder;

    private String bookName;
    private int count;
    private int price;
    private String thumbnail; 
}
