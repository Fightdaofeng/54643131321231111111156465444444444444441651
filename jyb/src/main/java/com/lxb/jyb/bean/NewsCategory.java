package com.lxb.jyb.bean;

import org.json.JSONObject;

public class NewsCategory {
	private String newsCategory;

	public NewsCategory(JSONObject obj) {
		if (obj != null) {
			newsCategory = obj.isNull("newsCategory") ? "" : obj
					.optString("newsCategory");
		}

	}

	public NewsCategory() {
		super();
		// TODO Auto-generated constructor stub
	}

	public NewsCategory(String newsCategory) {
		super();
		this.newsCategory = newsCategory;
	}

	@Override
	public String toString() {
		return "NewsCategory [newsCategory=" + newsCategory + "]";
	}

	public String getNewsCategory() {
		return newsCategory;
	}

	public void setNewsCategory(String newsCategory) {
		this.newsCategory = newsCategory;
	}

}
