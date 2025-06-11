package com.mysite.kyobo.detail;

import java.util.List;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "object", namespace = "http://www.aladin.co.kr/ttb/apiguide.aspx")
@XmlAccessorType(XmlAccessType.FIELD)
public class DetailAladinDto {
	@XmlElement(name = "item", namespace = "http://www.aladin.co.kr/ttb/apiguide.aspx")
	private List<AladinItem> item;

	public List<AladinItem> getItem() {
		return item;
	}

	public void setItem(List<AladinItem> item) {
		this.item = item;
	}
}
