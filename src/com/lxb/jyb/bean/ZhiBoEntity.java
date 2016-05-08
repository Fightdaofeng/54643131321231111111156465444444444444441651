package com.lxb.jyb.bean;

import java.util.ArrayList;

import org.json.JSONObject;

public class ZhiBoEntity {

	private String type;
	private ArrayList<Newsflash> newsflashs;
	private ArrayList<IndexEvent> indexEvents;

	public ZhiBoEntity(JSONObject obj) {
		if (obj != null) {
			type = obj.isNull("type") ? "" : obj.optString("type");
			if (type.equals("Newsflash")) {
				// JSONArray optJSONArray = obj.optJSONArray("entity");
				JSONObject optJSONObject = obj.optJSONObject("entity");
				newsflashs = new ArrayList<Newsflash>();
				Newsflash newsflash = new Newsflash(optJSONObject);
				newsflashs.add(newsflash);
			} else if (type.equals("IndexEvent")) {
				JSONObject optJSONObject = obj.optJSONObject("entity");
				indexEvents = new ArrayList<IndexEvent>();
				IndexEvent indexEvent = new IndexEvent(optJSONObject);
				indexEvents.add(indexEvent);
			}

		}
	}

	@Override
	public String toString() {
		return "ZhiBoEntity [type=" + type + ", newsflashs=" + newsflashs
				+ ", indexEvents=" + indexEvents + "]";
	}

	public ZhiBoEntity(String type, ArrayList<Newsflash> newsflashs,
			ArrayList<IndexEvent> indexEvents) {
		super();
		this.type = type;
		this.newsflashs = newsflashs;
		this.indexEvents = indexEvents;
	}

	public ZhiBoEntity() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public ArrayList<Newsflash> getNewsflashs() {
		return newsflashs;
	}

	public void setNewsflashs(ArrayList<Newsflash> newsflashs) {
		this.newsflashs = newsflashs;
	}

	public ArrayList<IndexEvent> getIndexEvents() {
		return indexEvents;
	}

	public void setIndexEvents(ArrayList<IndexEvent> indexEvents) {
		this.indexEvents = indexEvents;
	}
}
