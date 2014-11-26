package com.nes.search;

import java.util.ArrayList;
import java.util.List;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.impl.XMLResponseParser;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.client.solrj.response.TermsResponse;
import org.apache.solr.client.solrj.response.TermsResponse.Term;
import org.apache.solr.common.params.CommonParams;
import org.apache.solr.common.params.ModifiableSolrParams;
import org.apache.solr.common.params.TermsParams;
import org.apache.solr.common.util.NamedList;
import org.primefaces.component.autocomplete.AutoComplete;
import org.primefaces.component.button.Button;

public class ResultPageUI {
	AutoComplete searchTextBox;
	Button searchButton;
	AutoComplete placesBox;
	
	public AutoComplete getSearchTextBox() {
		return searchTextBox;
	}

	public Button getSearchButton() {
		return searchButton;
	}
	
	public void setSearchButton(Button searchButton) {
		this.searchButton = searchButton;
	}
	
	public void setSearchTextBox(AutoComplete searchTextBox) {
		this.searchTextBox = searchTextBox;
	}
	
	public AutoComplete getPlacesBox() {
		return placesBox;
	}
	
	public void setPlacesBox(AutoComplete placesBox) {
		this.placesBox = placesBox;
	}
	
	public List<String> getPlaces() {
		List<String> places = new ArrayList<String>();
		places.add("Buffalo");
		places.add("New York");
		places.add("Delhi");
		places.add("Bangalore");
		return places;
	}
	
	public List<String> suggestedQueryTerms(String query){
		List<String> result = new ArrayList<String>();
		HttpSolrServer solrServerForNews = null;
		XMLResponseParser writer = new XMLResponseParser();
		try {
			solrServerForNews = new HttpSolrServer("http://localhost:8983/solr/news");
			solrServerForNews.setParser(writer);
		} catch(Exception e) {
			e.printStackTrace(); 
		}
		ModifiableSolrParams suggestQuery = new ModifiableSolrParams();
		suggestQuery.set("spellcheck", true);
		suggestQuery.set("q", query);
		suggestQuery.set("qt","/suggest");
		System.out.println("Query Sent " + suggestQuery.toString());
		try {
			QueryResponse qr = solrServerForNews.query(suggestQuery);
			System.out.println(qr);
			NamedList<Object> response = qr.getResponse();
			@SuppressWarnings("unchecked")
			NamedList<Object> suggestResult = (NamedList<Object>) response.get("suggest");
			@SuppressWarnings("unchecked")
			NamedList<Object> suggestDictResult =  (NamedList<Object>) suggestResult.get("suggestdict");
			@SuppressWarnings("unchecked")
			NamedList<Object> termObject = (NamedList<Object>) suggestDictResult.get(suggestDictResult.getName(0));
			System.out.println("Term = " + termObject.toString() +  " " + termObject.getName(1));
			List<Object> termList = termObject.getAll(termObject.getName(1)); 
			List<Object> terms = (List<Object>) termList.get(0);
			System.out.println(terms.size());
			for(int i = 0; i < terms.size(); i++){
				System.out.println("Term " + i + " " + terms.get(i));
				NamedList<Object> term = (NamedList<Object>) terms.get(i);
				System.out.println("Term inside " + (term.get(term.getName(0))).toString());
				result.add((term.get(term.getName(0))).toString());
			}
		} catch (SolrServerException e) {
			System.out.println("EROR");
			e.printStackTrace();
		}
		result.add("searching....");
		return result;
	}
}
