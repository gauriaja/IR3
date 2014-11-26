package com.nes.ads;

public class RankedAdvertisement {

	private Double calculatedBidPrice;
	private Integer allocatedSlot;
	private Double totalScore;
	private Double qualityScore;
	private Advertisement ad;
	
	public RankedAdvertisement() {
		// TODO Auto-generated constructor stub
	}
	
	public Double getCalculatedBidPrice() {
		return calculatedBidPrice;
	}

	public Advertisement getAd() {
		return ad;
	}

	public void setAd(Advertisement ad) {
		this.ad = ad;
	}

	public void setCalculatedBidPrice(double calculatedBidPrice) {
		this.calculatedBidPrice = calculatedBidPrice;
	}

	public Integer getAllocatedSlot() {
		return allocatedSlot;
	}

	public void setAllocatedSlot(int allocatedSlot) {
		this.allocatedSlot = allocatedSlot;
	}

	public Double getTotalScore() {
		return totalScore;
	}

	public void setTotalScore(double totalScore) {
		this.totalScore = totalScore;
	}

	public Double getQualityScore() {
		return qualityScore;
	}

	public void setQualityScore(Double qualityScore) {
		this.qualityScore = qualityScore;
	}
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return super.toString();
	}
	

}
