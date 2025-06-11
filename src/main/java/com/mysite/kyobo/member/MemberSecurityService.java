package com.mysite.kyobo.member;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class MemberSecurityService implements UserDetailsService {
	private final MemberRepository memberRepository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<Member> optionalMember = memberRepository.findById(username);
		if(optionalMember.isEmpty()) {
			throw new UsernameNotFoundException("사용자를 찾을 수 없습니다 : " + username);
		}
		
		Member member = optionalMember.get();

		List<GrantedAuthority> authorizes = new ArrayList<>();
		if("admin".equals(username)) {
			authorizes.add(new SimpleGrantedAuthority(MemberRole.ADMIN.getValue()));
		} else {
			authorizes.add(new SimpleGrantedAuthority(MemberRole.USER.getValue()));
		}
		
		return new User(member.getId(), member.getPw(), authorizes);
	}
}
