package com.mysite.kyobo.index;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;


import com.mysite.kyobo.detail.Detail;
import com.mysite.kyobo.detail.DetailRepository;
import com.mysite.kyobo.review.ReviewRepository;
import com.mysite.kyobo.reviewcomment.ReviewCommentRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BookIndexService {
	private final BookIndexRepository bookIndexRepository;
	private final DetailRepository detailRepository;
	
	public List<BookIndexResponseDto> getindexList(String isbn){
		Detail detail = this.detailRepository.findByisbn(isbn);
		List<BookIndex> indexList = this.bookIndexRepository.findByDetail(detail);
		
		//dto 로 변환 
		 return indexList.stream()
			        .map(i -> new BookIndexResponseDto(i.getIndexName()))
			        .collect(Collectors.toList());
		
		
		
	}
	
	

}
