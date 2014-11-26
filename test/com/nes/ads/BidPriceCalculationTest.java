package com.nes.ads;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

import mockit.Deencapsulation;
import mockit.Mock;
import mockit.MockUp;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class BidPriceCalculationTest {

	List<Advertisement> ads;
	BidPriceCalculation obj;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		
	}
	@Before
	public void setUp(){
		ads = new ArrayList<Advertisement>();
		double baseRelevancyScore = 1;
		double landingPageRelevancyScore = 1;
		double enteredBidPrice = 5.0;
		for(int i=0;i<10;i++){
			Advertisement ad = new Advertisement();
			ad.setRelevancyScore(baseRelevancyScore);
			baseRelevancyScore-=.12;
			ad.setLandingPageRelevancyScore(landingPageRelevancyScore);
			landingPageRelevancyScore-=.12;
			ad.setEnteredBidPrice(enteredBidPrice++);
			ad.setId(String.valueOf(i+1));
			ads.add(ad);
		}
		obj = new BidPriceCalculation();
	}
	
	@After
	public void tearDown(){
		ads=null;	
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Test
	public void testCalculateBidPrice() {
		Queue<RankedAdvertisement> rankedAds =obj.calculateTrueBidPriceAndRankAds(ads, 3);
		new MockUp<RankedAdvertisement>() {
			@Mock
		public String toString(){
			return "ID:"+getMockInstance().getAd().getId()+", QS: "+getMockInstance().getQualityScore() +", BP: "+getMockInstance().getAd().getEnteredBidPrice()+", TBP: "+getMockInstance().getCalculatedBidPrice()+"\n";
		}
		};
		System.out.println(rankedAds);
	}
	
	@Test
	public void scoreAndSortAds(){
		
		List<RankedAdvertisement> rankedAds = Deencapsulation.invoke(obj, "scoreAndSortAds", ads);
		new MockUp<RankedAdvertisement>() {
			@Mock
			public String toString(){
				return "ID:"+getMockInstance().getAd().getId()+", TS: "+getMockInstance().getTotalScore()+", BP: "+getMockInstance().getAd().getEnteredBidPrice()+",QS: "+getMockInstance().getQualityScore()+", LRS: "+getMockInstance().getAd().getLandingPageRelevancyScore()+", RS: "+getMockInstance().getAd().getRelevancyScore()+"\n";
			}
		};
		System.out.println(rankedAds);
	} 
}
 