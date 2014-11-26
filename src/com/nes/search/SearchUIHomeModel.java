package com.nes.search;

import java.io.Serializable;

public class SearchUIHomeModel implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String searchText;
	
	public SearchUIHomeModel() {
		// TODO Auto-generated constructor stub
	}

	public String getSearchText() {
		return searchText;
	}

	public void setSearchText(String searchText) {
		this.searchText = searchText;
	}
	
}
