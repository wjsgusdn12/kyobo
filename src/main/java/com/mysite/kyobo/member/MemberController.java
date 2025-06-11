package com.mysite.kyobo.member;

import java.security.Principal;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import lombok.RequiredArgsConstructor;

@RequestMapping("/member")
@Controller
@RequiredArgsConstructor
public class MemberController {

	private final MemberService memberService;
	private final PasswordEncoder passwordEncoder;
	
	@GetMapping("/join")
    public String joinForm(Model model) {
        model.addAttribute("dto", new MemberDto());
        return "join";
    }
	
	@GetMapping("/check-id")
	@ResponseBody
	public ResponseEntity<Boolean> checkId(@RequestParam("id") String id){
		boolean checkId = memberService.findById(id).isPresent();
		return ResponseEntity.ok(checkId);
	}
	
	@GetMapping("/check-email")
	@ResponseBody
	public ResponseEntity<Boolean> checkEmail(@RequestParam("email") String email){
	    boolean exists = memberService.findByEmail(email).isPresent();
	    return ResponseEntity.ok(exists);
	}
	
	@GetMapping("/check-phone")
	@ResponseBody
	public ResponseEntity<Boolean> checkPhone(@RequestParam("phone") String phone){
	    boolean exists = memberService.findByPhone(phone).isPresent();
	    return ResponseEntity.ok(exists);
	}
	
	@PostMapping("/join")
	public String join(MemberDto dto) {
		memberService.save(dto);
		return "redirect:/login";
	}
	
	@PostMapping("/my_page_login")
    @ResponseBody
    public Map<String, Object> myPageLogin(@RequestParam("pw") String inputPw, Principal principal) {
        String username = principal.getName(); // 로그인한 사용자 정보
        Optional<Member> optionalMember = memberService.findById(username);
        Map<String, Object> response = new HashMap<>();
        if (optionalMember.isEmpty()) {
            response.put("success", false);
            return response;
        }
        Member member = optionalMember.get();
        if (!passwordEncoder.matches(inputPw, member.getPw())) {
            response.put("success", false);
            return response;
        }
        response.put("success", true);
        return response;
    }
	
	@GetMapping("/my_page")
	public String myPage(Principal principal, Model model) {
		String username = principal.getName();
		Optional<Member> optionalMember = memberService.findById(username);
		if(optionalMember.isPresent()) {
			Member member = optionalMember.get();
			model.addAttribute("member", member);
		} else {
			model.addAttribute("error", "사용자 정보를 불러올 수 없습니다.");
		}
		return "my_page";
	}
	
	@PostMapping("/update")
	public String updateMember(@ModelAttribute MemberDto dto, Principal principal, Model model) {
		String username = principal.getName();
		memberService.updateMember(username, dto);
		return "redirect:/member/my_page";
	}
	
	@PostMapping("/find-result")
	@ResponseBody
	public Map<String, Object> findResult(@RequestParam("name") String name, @RequestParam("phone") String phone) {
		Member member = memberService.findIdByNameAndPhone(name, phone);
		Map<String, Object> response = new HashMap<>();
		if(member != null) {
			response.put("redirect", "/kyobo/member/find-result?name="+name+"&phone="+phone);
		} else {
			response.put("error", "해당하는 사용자를 찾을 수 없습니다.");
		}
		return response;
	}
	
	@GetMapping("/find-result")
	public String findResult(@RequestParam("name") String name,
			@RequestParam("phone") String phone, Model model) {
		Member member = memberService.findIdByNameAndPhone(name, phone);
		model.addAttribute("member", member);
		return "find_result";
	}

	@PostMapping("/reset-password")
	@ResponseBody
	public Map<String, Object> resetPassword(@RequestParam("id") String id, @RequestParam("name") String name,
			@RequestParam("phone") String phone) {
		Member member = memberService.findByIdAndNameAndPhone(id, name, phone);
		Map<String, Object> response = new HashMap<>();
		if(member != null) {
			response.put("redirect", "/kyobo/member/reset-password?id="+id+"&name="+name+"&phone="+phone);
		} else {
			response.put("error", "입력한 정보와 일치하는 회원이 없습니다.");
		}
		return response;
	}
	
	@GetMapping("/reset-password")
	public String resetPassword(@RequestParam("id") String id, @RequestParam("name") String name,
			@RequestParam("phone") String phone, Model model) {
		System.out.println("id: " + id + ", name: " + name + ", phone: " + phone);
		Member member = memberService.findByIdAndNameAndPhone(id, name, phone);
		model.addAttribute("member", member);
		return "reset-password";
	}
	
	@PostMapping("/reset-password/do")
	@ResponseBody
	public ResponseEntity<String> resetPw(@RequestBody PasswordResetRequest request){
		String id = request.getId();
		String newPw = request.getPw();
		Member member = memberService.findMemberById(id);
		
		if(member == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("해당 사용자를 찾을 수 없습니다.");
		}
		
		String encodePw = passwordEncoder.encode(newPw);
		member.setPw(encodePw);
		memberService.save(member);
		
		return ResponseEntity.ok("비밀번호가 성공적으로 변경되었습니다.");
	}
	
}
