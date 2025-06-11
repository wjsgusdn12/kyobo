package com.mysite.kyobo.review;

import java.security.Principal;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.mysite.kyobo.detail.AladinItem;
import com.mysite.kyobo.detail.Detail;
import com.mysite.kyobo.detail.DetailRepository;
import com.mysite.kyobo.detail.DetailService;
import com.mysite.kyobo.index.BookIndexResponseDto;
import com.mysite.kyobo.index.BookIndexService;
import com.mysite.kyobo.member.Member;
import com.mysite.kyobo.member.MemberService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class ReviewController {
	
	private final ReviewService reviewService;
	private final DetailRepository detailRepository;
	private final DetailService detailService;
	private final MemberService memberService;
	private final BookIndexService bookIndexService;
	
	@PostMapping("/review/create")
	public String reviewCreate(@Valid ReviewForm reviewForm, BindingResult bindingResult, @RequestParam(value="score") Integer score,
			@RequestParam(value="isbn") String isbn, Model model, Principal principal) {
		Member member = this.memberService.findMemberById(principal.getName());
		// 리뷰 등록하기 
		if (bindingResult.hasErrors()) {
	        // 다시 데이터 세팅
	        Detail detail = this.detailRepository.findByisbn(isbn);
	        AladinItem item = this.detailService.searchBookAndParse(isbn).getItem().get(0);
	      //목차 
			List<BookIndexResponseDto> indexDtoList = this.bookIndexService.getindexList(isbn);
	        // 모델에 필요한 것들 다시 넣기
	        model.addAttribute("isbn", isbn);
	        model.addAttribute("item", item);
	        model.addAttribute("detail", detail);
	        model.addAttribute("reviewForm", reviewForm); // 반드시 다시 넣어야 함!
	        model.addAttribute("indexDtoList", indexDtoList);

	        return "detail"; // 템플릿 이름
	    }
		this.reviewService.reviewCreate(reviewForm.getReview(), isbn, score, member);
		return "redirect:/detail/" + isbn ; // 어디 isbn 인지 같이 넘겨야함 
	}
}