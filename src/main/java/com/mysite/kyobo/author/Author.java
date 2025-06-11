package com.mysite.kyobo.author;

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
public class Author {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_author_idx")
	@SequenceGenerator(
		name = "seq_author_idx",
		sequenceName = "seq_author_idx",
		allocationSize = 1
	)
	private int authorIdx;
	
	private String authorName;
	
}
