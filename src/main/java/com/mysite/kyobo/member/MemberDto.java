package com.mysite.kyobo.member;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberDto {
	private String id;
	private String pw;
	private String name;
	private String phone;
	private String email;
	private LocalDateTime joinDate;
}
