package com.nes.search;

import java.io.Serializable;

public class Ads implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -461952613644309L;
	private String heading;
	private String desc;
	private String url;
	public Ads(String heading, String desc, String url) {
		this.setHeading(heading);
		this.setDesc(desc);
		this.setUrl(url);
	}
	public String getHeading() {
		return heading;
	}
	public void setHeading(String heading) {
		this.heading = heading;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	
}
