package com.mysite.kyobo.detail;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class AladinItem {

	@XmlElement(name = "title", namespace = "http://www.aladin.co.kr/ttb/apiguide.aspx")
	private String title;

	@XmlElement(name = "link", namespace = "http://www.aladin.co.kr/ttb/apiguide.aspx")
	private String link;

	@XmlElement(name = "author", namespace = "http://www.aladin.co.kr/ttb/apiguide.aspx")
	private String author;

	@XmlElement(name = "pubDate", namespace = "http://www.aladin.co.kr/ttb/apiguide.aspx")
	private String pubDate;

	@XmlElement(name = "description", namespace = "http://www.aladin.co.kr/ttb/apiguide.aspx")
	private String description;

	// getter & setter
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getPubDate() {
		return pubDate;
	}

	public void setPubDate(String pubDate) {
		this.pubDate = pubDate;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
