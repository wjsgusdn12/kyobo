package com.mysite.kyobo.index;

import com.mysite.kyobo.detail.Detail;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name="book_index")
public class BookIndex {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_index_idx")
	@SequenceGenerator(
	       name = "seq_index_idx",          
	       sequenceName = "seq_index_idx",   
	       allocationSize = 1
	   )
	private Integer indexIdx;
	
	@ManyToOne
	private Detail detail;
	
	private String indexName;

}
