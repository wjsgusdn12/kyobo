package com.mysite.kyobo.member;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Member {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_member_idx")
	@SequenceGenerator(
	    name = "seq_member_idx",           // ✅ JPA에서 사용할 시퀀스 생성기 이름 (임의로 정할 수 있음)
	    sequenceName = "seq_member_idx",   // ✅ 실제 Oracle 시퀀스 이름
	    allocationSize = 1
	)
	private int memberIdx;
	
	@Column(unique=true)
	private String id;
	
	private String pw;
	
	private String name;
	
	@Column(unique=true)
	private String phone;
	
	@Column(unique=true)
	private String email;
	
	@Column(name = "join_date", nullable = false, updatable = false, columnDefinition = "TIMESTAMP DEFAULT SYSDATE")
    private LocalDateTime joinDate;
}
