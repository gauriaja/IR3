package com.nes.search;

import java.io.Serializable;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.bean.ManagedProperty;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;

public class ResultPageUIModel implements Serializable {

	private static final long serialVersionUID = 6020069918911226138L;
	private String searchText;
	private List<NewsArticle> newsArticles;
	private Date fromDate;
	private Date toDate;
	private List<String> categories;
	private List<Ads> ads;
	private String place;
	private String adTitle;
	private String adDesc;
	private String adKeywords;
	private String adBidPrice;
	private String location;
	private String budgetLimit;
	public ResultPageUIModel() {
		// TODO Auto-generated constructor stub
	}

	public String getSearchText() {
		return searchText;
	}

	public void setNewsArticles(List<NewsArticle> newsArticles) {
		this.newsArticles = newsArticles;
	}

	public List<NewsArticle> getNewsArticles() {
		return newsArticles;
	}

	public Date getFromDate() {
		return fromDate;
	}

	public void setFromDate(Date fromDate) {
		this.fromDate = fromDate;
	}

	public Date getToDate() {
		return toDate;
	}

	public void setToDate(Date toDate) {
		this.toDate = toDate;
	}

	public List<String> getCategories() {
		categories = new ArrayList<String>();
		categories.add("Category 1");
		categories.add("Category 2");
		return categories;
	}

	public void setCategories(List<String> categories) {
		this.categories = categories;
	}

	public List<Ads> getAds() {
		return ads;
	}

	public void setAds(List<Ads> ads) {
		this.ads = ads;
	}

	public String getPlace() {
		return place;
	}

	public void setPlace(String place) {
		this.place = place;
	}

	public String getAdTitle() {
		return adTitle;
	}

	public void setAdTitle(String adTitle) {
		this.adTitle = adTitle;
	}

	public String getAdDesc() {
		return adDesc;
	}

	public void setAdDesc(String adDesc) {
		this.adDesc = adDesc;
	}

	public String getAdKeywords() {
		return adKeywords;
	}

	public void setAdKeywords(String adKeywords) {
		this.adKeywords = adKeywords;
	}

	public String getAdBidPrice() {
		return adBidPrice;
	}

	public void setAdBidPrice(String adBidPrice) {
		this.adBidPrice = adBidPrice;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getBudgetLimit() {
		return budgetLimit;
	}

	public void setBudgetLimit(String budgetLimit) {
		this.budgetLimit = budgetLimit;
	}
	
	public void setSearchText(ActionEvent event) {
		searchText = (String) event.getComponent().getAttributes().get("searchText");
	}
	
	private void parseNewsArticlesResults(SolrDocumentList solrDLNews) {
		List<NewsArticle> listNA = new ArrayList<NewsArticle>();
		for(int i=0; i<solrDLNews.size(); i++) {
			SolrDocument sd = solrDLNews.get(i);
			listNA.add(new NewsArticle(sd.getFieldValue("title").toString(), "snippet" + i, sd.getFieldValue("content").toString()));
		}
		setNewsArticles(listNA);
	}
	
	private void parseAdsArticlesResults(SolrDocumentList solrDLAds) {
		List<Ads> listAA = new ArrayList<Ads>();
		for(int i=0; i<solrDLAds.size(); i++) {
			SolrDocument sd = solrDLAds.get(i);
			listAA.add(new Ads("Ad" + i, sd.getFieldValue("content").toString(), sd.getFieldValue("SiteLink").toString()));
		}
		setAds(listAA);
	}
	
	public String submitQuery() throws MalformedURLException, SolrServerException {
		HttpSolrServer solrForNews = new HttpSolrServer("http://localhost:8983/solr/news");
	    HttpSolrServer solrForAds = new HttpSolrServer("http://localhost:8983/solr/advertisement");
	    SolrQuery queryForNews = new SolrQuery();
	    SolrQuery queryForAds = new SolrQuery();
	    queryForNews.setQuery(searchText);
	    queryForAds.setQuery(searchText);
	    QueryResponse responseForNews = null;
	    QueryResponse responseForAds = null;
		try {
			responseForNews = solrForNews.query(queryForNews);
			responseForAds = solrForAds.query(queryForAds);
		} catch (SolrServerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    SolrDocumentList sdlForNews = responseForNews.getResults();
	    SolrDocumentList sdlForAds = responseForAds.getResults();
	    parseNewsArticlesResults(sdlForNews);
	    parseAdsArticlesResults(sdlForAds);
	    return "result";
	}
}
