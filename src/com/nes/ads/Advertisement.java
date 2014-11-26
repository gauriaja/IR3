package com.nes.ads;

public class Advertisement {

	private String id;
	private String title;
	private String url;
	private String description;
	private Double relevancyScore;
	private Double landingPageRelevancyScore;
	private Double enteredBidPrice;
	
	
	public Double getEnteredBidPrice() {
		return enteredBidPrice;
	}

	public void setEnteredBidPrice(double enteredBidPrice) {
		this.enteredBidPrice = enteredBidPrice;
	}

	public Advertisement() {
		// TODO Auto-generated constructor stub
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Double getRelevancyScore() {
		return relevancyScore;
	}

	public void setRelevancyScore(double relevancyScore) {
		this.relevancyScore = relevancyScore;
	}

	public Double getLandingPageRelevancyScore() {
		return landingPageRelevancyScore;
	}

	public void setLandingPageRelevancyScore(double landingPageRelevancyScore) {
		this.landingPageRelevancyScore = landingPageRelevancyScore;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
}
