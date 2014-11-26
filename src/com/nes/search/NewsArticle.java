package com.nes.search;

import java.io.Serializable;

public class NewsArticle implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -3206693248905350073L;
	private String heading;
	private String snippet;
	private String content;
	public NewsArticle(String heading, String snippet, String content) {
		this.heading = heading;
		this.snippet = snippet;
		this.content = content;
	}
	public String getHeading() {
		return heading;
	}
	public void setHeading(String heading) {
		this.heading = heading;
	}
	public String getSnippet() {
		return snippet;
	}
	public void setSnippet(String snippet) {
		this.snippet = snippet;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
}
