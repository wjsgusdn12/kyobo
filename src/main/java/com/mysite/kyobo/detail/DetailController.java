package com.mysite.kyobo.detail;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.mysite.kyobo.index.BookIndexResponseDto;
import com.mysite.kyobo.index.BookIndexService;
import com.mysite.kyobo.review.ReviewForm;
import com.mysite.kyobo.review.ReviewResponseDto;
import com.mysite.kyobo.review.ReviewService;
import com.mysite.kyobo.reviewcomment.ReviewCommentItemDto;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class DetailController {

	private final DetailService detailService;
	private final DetailRepository detailRepository;
	private final ReviewService reviewService;
	private final BookIndexService bookIndexService;
	
	@GetMapping("/detail/{isbn}")
	public String detail(Model model, @PathVariable("isbn") String isbn, ReviewForm reviewFrom,
	                     @RequestParam(value = "page", defaultValue = "0") int page,
	                     @AuthenticationPrincipal UserDetails userDetails) {

	    Detail detail = this.detailRepository.findByisbn(isbn);

	    AladinItem item = null;
	    try {
	        DetailAladinDto dto = detailService.searchBookAndParse(isbn);
	        item = dto.getItem().get(0);
	    } catch (Exception e) {
	        e.printStackTrace();
	    }

	    int size = 3;
	    Page<ReviewResponseDto> reviewPage = reviewService.getReviewListWithComments(isbn, page, size);

	    String pubDatePre = item.getPubDate();
	    DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
	    DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("yyyy년 MM월 dd일");

	    LocalDate date = LocalDate.parse(pubDatePre, inputFormatter);
	    String pubDate = date.format(outputFormatter);

	    List<BookIndexResponseDto> indexDtoList = this.bookIndexService.getindexList(isbn);
	    boolean loginCheck = (userDetails != null);

	    model.addAttribute("isbn", isbn);
	    model.addAttribute("detail", detail);
	    model.addAttribute("item", item);
	    model.addAttribute("paging", reviewPage);
	    model.addAttribute("twoDaysLater", LocalDate.now().plusDays(2));
	    model.addAttribute("pubDate", pubDate);
	    model.addAttribute("indexDtoList", indexDtoList);
	    model.addAttribute("loginCheck", loginCheck);
	    return "detail";
	}

	//댓글 작성 하기 
	@PostMapping("/comment/fragment")
	public String getCommentFragment(@RequestBody ReviewCommentItemDto comment, Model model) {
		model.addAttribute("comment", comment);
		return "commentItem :: comment"; // Thymeleaf fragment 이름
	}

}
