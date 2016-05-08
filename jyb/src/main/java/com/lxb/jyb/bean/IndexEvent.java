package com.lxb.jyb.bean;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class IndexEvent {
	private String name;// "谘商会消费者信心指数",
	private String country;// : "美国",
	private String basicIndexId;// ": "103",
	private String period;
	private String importance;
	private String lastValue;
	private String predictValue;
	private String value;
	private String year;
	private String positiveItem;
	private String negativeItem;
	private String level;
	private String url;
	private String date;
	private String time;

	public IndexEvent(JSONObject obj) {
		name = obj.isNull("name") ? "" : obj.optString("name");
		country = obj.isNull("country") ? "" : obj.optString("country");
		basicIndexId = obj.isNull("basicIndexId") ? "" : obj
				.optString("basicIndexId");
		period = obj.isNull("period") ? "" : obj.optString("period");
		importance = obj.isNull("importance") ? "" : obj
				.optString("importance");
		lastValue = obj.isNull("lastValue") ? "" : obj.optString("lastValue");
		predictValue = obj.isNull("predictValue") ? "" : obj
				.optString("predictValue");
		value = obj.isNull("value") ? "" : obj.optString("value");
		year = obj.isNull("year") ? "" : obj.optString("year");
		positiveItem = obj.isNull("positiveItem") ? "" : obj
				.optString("positiveItem");
		negativeItem = obj.isNull("negativeItem") ? "" : obj
				.optString("negativeItem");
		level = obj.isNull("level") ? "" : obj.optString("level");
		url = obj.isNull("url") ? "" : obj.optString("url");
		date = obj.isNull("date") ? "" : obj.optString("date");
		time = obj.isNull("time") ? "" : obj.optString("time");
	}

	@Override
	public String toString() {
		return "IndexEvent [name=" + name + ", country=" + country
				+ ", basicIndexId=" + basicIndexId + ", period=" + period
				+ ", importance=" + importance + ", lastValue=" + lastValue
				+ ", predictValue=" + predictValue + ", value=" + value
				+ ", year=" + year + ", positiveItem=" + positiveItem
				+ ", negativeItem=" + negativeItem + ", level=" + level
				+ ", url=" + url + ", date=" + date + ", time=" + time + "]";
	}

	public IndexEvent(String name, String country, String basicIndexId,
			String period, String importance, String lastValue,
			String predictValue, String value, String year,
			String positiveItem, String negativeItem, String level, String url,
			String date, String time) {
		super();
		this.name = name;
		this.country = country;
		this.basicIndexId = basicIndexId;
		this.period = period;
		this.importance = importance;
		this.lastValue = lastValue;
		this.predictValue = predictValue;
		this.value = value;
		this.year = year;
		this.positiveItem = positiveItem;
		this.negativeItem = negativeItem;
		this.level = level;
		this.url = url;
		this.date = date;
		this.time = time;
	}

	public IndexEvent() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getBasicIndexId() {
		return basicIndexId;
	}

	public void setBasicIndexId(String basicIndexId) {
		this.basicIndexId = basicIndexId;
	}

	public String getPeriod() {
		return period;
	}

	public void setPeriod(String period) {
		this.period = period;
	}

	public String getImportance() {
		return importance;
	}

	public void setImportance(String importance) {
		this.importance = importance;
	}

	public String getLastValue() {
		return lastValue;
	}

	public void setLastValue(String lastValue) {
		this.lastValue = lastValue;
	}

	public String getPredictValue() {
		return predictValue;
	}

	public void setPredictValue(String predictValue) {
		this.predictValue = predictValue;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public String getPositiveItem() {
		return positiveItem;
	}

	public void setPositiveItem(String positiveItem) {
		this.positiveItem = positiveItem;
	}

	public String getNegativeItem() {
		return negativeItem;
	}

	public void setNegativeItem(String negativeItem) {
		this.negativeItem = negativeItem;
	}

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}
}
