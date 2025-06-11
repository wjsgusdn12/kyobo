package com.mysite.kyobo.detail;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.mysite.kyobo.DataNotFoundException;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.Unmarshaller;
import java.io.StringReader;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class DetailService {
	@Autowired
	private RestTemplate restTemplate; // RestTemplate는 스프링에서 외부 API를 호출할 때 쓰는 도구
	@Autowired
	private DetailRepository detailRepository;

	public String searchBooksByIsbn(String isbn13) throws Exception {
	    String apiKey = "ttbcba10082012001";

	    // 1단계 없이, ISBN으로 바로 상세 정보 조회
	    String lookupUrl = "http://www.aladin.co.kr/ttb/api/ItemLookUp.aspx"
	            + "?ttbkey=" + apiKey
	            + "&itemIdType=ISBN"
	            + "&ItemId=" + isbn13
	            + "&output=xml"
	            + "&Version=20131101";

	    String detailXml = restTemplate.getForObject(lookupUrl, String.class);
	    System.out.println("상세 정보 XML: \n" + detailXml); // 확인용

	    return detailXml;
	}
	
	//xml 형식을 dto 로 변환하는 메서드 
	private DetailAladinDto parseXml(String xml) throws Exception {
        JAXBContext context = JAXBContext.newInstance(DetailAladinDto.class);
        Unmarshaller unmarshaller = context.createUnmarshaller();
        return (DetailAladinDto) unmarshaller.unmarshal(new StringReader(xml));
    }
	
	//알라딘 api 에서 받은 xml -> dto  파싱 
	public DetailAladinDto searchBookAndParse(String isbn13) {
		try {
			String xml = searchBooksByIsbn(isbn13); // 1. XML 요청
			return parseXml(xml); // 2. XML → DTO 파싱
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public Detail findWithReviewsAndCommentsByIsbn(@Param("postIsbn") String postIsbn) {
		return this.detailRepository.findWithReviewsAndCommentsByIsbn(postIsbn)
				.orElseThrow(()-> new DataNotFoundException("해당 게시글은 없습니다."));
	}
}