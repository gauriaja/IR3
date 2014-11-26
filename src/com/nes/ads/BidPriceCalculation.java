package com.nes.ads;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;

public class BidPriceCalculation {
	
	public BidPriceCalculation() {
	}
	
	private List<RankedAdvertisement> scoreAndSortAds(List<Advertisement> ads){
		List<RankedAdvertisement> scoredAdvertisements = new ArrayList<RankedAdvertisement>();
		for(Advertisement ad: ads){
 			RankedAdvertisement theAd = new RankedAdvertisement();
 			theAd.setAd(ad);
 			theAd.setQualityScore(ad.getRelevancyScore()+ad.getLandingPageRelevancyScore());
 			theAd.setTotalScore(theAd.getQualityScore()*ad.getEnteredBidPrice());
 			scoredAdvertisements.add(theAd);
		}
 		//Sort the list by total score
 		Collections.sort(scoredAdvertisements,new Comparator<RankedAdvertisement>() {
			@Override
			public int compare(RankedAdvertisement o1, RankedAdvertisement o2) {
				return o2.getTotalScore().compareTo(o1.getTotalScore());
			}
		});
 		return scoredAdvertisements;
	}
	
	public Queue<RankedAdvertisement> calculateTrueBidPriceAndRankAds(List<Advertisement> ads,int noOfSlots){
		List<RankedAdvertisement> scoredAdvertisements = scoreAndSortAds(ads);
		return calculateTrueBidPriceAndAllocateSlot(scoredAdvertisements, noOfSlots);
	}
	
	private Queue<RankedAdvertisement> calculateTrueBidPriceAndAllocateSlot(List<RankedAdvertisement> scoredAdvertisements,int noOfSlots ){
		
 		PriorityQueue<RankedAdvertisement> rankedAds = new PriorityQueue<RankedAdvertisement>(1, new Comparator<RankedAdvertisement>() {
			@Override
			public int compare(RankedAdvertisement o1, RankedAdvertisement o2) {
				return o2.getTotalScore().compareTo(o1.getTotalScore());
			}
		});
 		//Need noOfSlots+1 for calculation using vickrey auction
// 		scoredAdvertisements =  scoredAdvertisements.subList(0, scoredAdvertisements.size()> noOfSlots+1 ? noOfSlots+1 :scoredAdvertisements.size());
 		RankedAdvertisement previousAd = null; 
 		Iterator<RankedAdvertisement> iteratorScoredAdvertisements = scoredAdvertisements.iterator();
 		int count=0;
 		while(count<noOfSlots+1 && iteratorScoredAdvertisements.hasNext()){
 			RankedAdvertisement scoredAd= iteratorScoredAdvertisements.next();
 			if(previousAd!=null){
 				previousAd.setCalculatedBidPrice(calculateEstimatedBidPrice(previousAd.getQualityScore(), scoredAd.getTotalScore()));
 				rankedAds.add(previousAd);
 			}
 			previousAd = scoredAd;
 			count++;
 		}
 		//It means that the iterator had terminated, hence add the last Ad
 		if(count<noOfSlots+1){
 			previousAd.setCalculatedBidPrice(previousAd.getAd().getEnteredBidPrice());
 			rankedAds.add(previousAd);
 		}
 		return rankedAds; 
	}
	
	private Double calculateEstimatedBidPrice(double qualityScore, double totalScore){
		return totalScore/qualityScore;
	}
}
