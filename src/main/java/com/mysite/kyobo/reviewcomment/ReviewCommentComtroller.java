package com.mysite.kyobo.reviewcomment;

import java.security.Principal;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mysite.kyobo.member.Member;
import com.mysite.kyobo.member.MemberService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class ReviewCommentComtroller {
	private final ReviewCommentService commentService;
	private final MemberService memberService;
	
	@PostMapping("/comment/create")
	@ResponseBody
	public ResponseEntity<Map<String, Object>> commentcreate(
	        @RequestParam("isbn") Long isbn,
	        @RequestParam("comment") String comment,
	        @RequestParam("reviewIdx") Integer reviewIdx, Principal principal) {
		Member member = this.memberService.findMemberById(principal.getName());
	    ReviewComment commentEntity = commentService.createComment(reviewIdx, comment, member); // 반환하게 바꿈

	    Map<String, Object> result = new HashMap<>();
	    result.put("writer", member.getId()); // 추후 로그인 사용자로 변경 가능
	    result.put("content", commentEntity.getContent());
	    result.put("date", commentEntity.getCreateDate().toLocalDate().toString()); // 또는 commentEntity.getCreateDate().toLocalDate().toString()
	    result.put("commentIdx", commentEntity.getCommentIdx());

	    return ResponseEntity.ok(result);
	}
	
	@PostMapping("/comment/update")
	@ResponseBody
	public ResponseEntity<Map<String,Object>> commentupdate(@RequestParam("commentIdx") Integer commentIdx, 
			@RequestParam("commentData") String commentData){
		ReviewComment reviewComment = this.commentService.updateComment(commentIdx, commentData);
		Map<String, Object> result = new HashMap<>();
		result.put("writer", "cb****");
		result.put("data", reviewComment.getCreateDate().toLocalDate().toString());
		result.put("content", reviewComment.getContent());
		
		return ResponseEntity.ok(result);
		
	}
	
	
	@PostMapping("/comment/delete")
	@ResponseBody
	public String commentDelete(@RequestParam("commentIdx") Integer commentIdx) {
		this.commentService.deleteComment(commentIdx);
		return "success";
		
	}

	
	
	
	
	
	
	
	

}
