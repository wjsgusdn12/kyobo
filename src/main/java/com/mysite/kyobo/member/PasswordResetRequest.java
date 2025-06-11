package com.mysite.kyobo.member;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PasswordResetRequest {
	private String id;
	private String pw;
}
