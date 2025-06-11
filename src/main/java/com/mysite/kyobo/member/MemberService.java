package com.mysite.kyobo.member;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MemberService {
	private final MemberRepository memberRepository;
	private final PasswordEncoder passwordEncoder;
	
	public void save(MemberDto dto) {
		
		if(memberRepository.existsByIdCustom(dto.getId()) == 1) {
            throw new IllegalStateException("이미 존재하는 아이디입니다.");
        }
        if(memberRepository.existsByEmailCustom(dto.getEmail()) == 1) {
            throw new IllegalStateException("이미 존재하는 이메일입니다.");
        }
        if(memberRepository.existsByPhoneCustom(dto.getPhone()) == 1) {
            throw new IllegalStateException("이미 존재하는 전화번호입니다.");
        }
		
		Member member = new Member();
	    member.setId(dto.getId());
	    member.setPw(passwordEncoder.encode(dto.getPw()));
	    member.setName(dto.getName());
	    member.setPhone(dto.getPhone());
	    member.setEmail(dto.getEmail());
	    member.setJoinDate(dto.getJoinDate() == null ? LocalDateTime.now() : dto.getJoinDate());
	    memberRepository.save(member);
	}
	
	public Optional<Member> findById(String id) {
        return memberRepository.findById(id);
    }
	
    public Optional<Member> findByEmail(String email) {
        return memberRepository.findByEmail(email);
    }

    public Optional<Member> findByPhone(String phone) {
        return memberRepository.findByPhone(phone);
    }
    
    public boolean isEmailExists(String email) {
	    return memberRepository.existsByEmail(email);
	}

	public boolean isPhoneExists(String phone) {
	    return memberRepository.existsByPhone(phone);
	}
	
	public Member findMemberById(String id) {
        return memberRepository.findById(id).orElse(null);
    }
	
	public void save(Member member) {
        memberRepository.save(member);
    }
	
	public void updateMember(String username, MemberDto dto) {
		Member member = memberRepository.findById(username)
				.orElseThrow(()->new UsernameNotFoundException("사용자 없음"));
		
		if(dto.getPw() != null && !dto.getPw().isEmpty()) {
			member.setPw(passwordEncoder.encode(dto.getPw()));
		}
		
		member.setName(dto.getName());
		member.setEmail(dto.getEmail());
		member.setPhone(dto.getPhone());
		
		memberRepository.save(member);
	}
	
	public Member findIdByNameAndPhone(String name, String phone) {
		Member member = memberRepository.findIdByNameAndPhone(name, phone);
		if(member != null) {
			return member;
		} else {
			return null;
		}
	}
	
	public Member findByIdAndNameAndPhone(String id, String name, String phone) {
	    System.out.println("Searching for member with id: " + id + ", name: " + name + ", phone: " + phone);
	    return memberRepository.findByIdAndNameAndPhone(id, name, phone);
	}
}