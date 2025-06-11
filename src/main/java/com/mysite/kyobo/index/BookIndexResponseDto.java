package com.mysite.kyobo.index;

import com.mysite.kyobo.detail.Detail;

import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BookIndexResponseDto {
	public BookIndexResponseDto(String indexName) {
		this.indexName = indexName;
	}
	
	private String indexName;
	

}
