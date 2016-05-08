package com.lxb.jyb.bean;

import org.json.JSONObject;

public class CalendarEntity implements Comparable<CalendarEntity> {
	// 货币名称
	private String name;
	// 国家名称
	private String country;
	// 指标ID
	private String basicIndexId;
	// 指标时期
	private String period;
	// 指标重要性
	private String importance;
	// 预期值
	private String predictValue;
	// 前值
	private String lastValue;
	// 公布值
	private String value;
	// 年份
	private String year;
	// 利多项
	private String positiveItem;
	// 利空项
	private String negativeItem;
	// 指标级数
	private String level;
	// 指标内链接
	private String url;
	// 指标日期
	private String date;
	// 指标时间
	private String time;

	public CalendarEntity(JSONObject obj) {
		if (obj != null) {
			name = obj.isNull("name") ? "" : obj.optString("name");
			country = obj.isNull("country") ? "" : obj.optString("country");
			basicIndexId = obj.isNull("basicIndexId") ? "" : obj
					.optString("basicIndexId");
			period = obj.isNull("period") ? "" : obj.optString("period");
			importance = obj.isNull("importance") ? "" : obj
					.optString("importance");
			lastValue = obj.isNull("lastValue") ? "" : obj
					.optString("lastValue");
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

	public String getPredictValue() {
		return predictValue;
	}

	public void setPredictValue(String predictValue) {
		this.predictValue = predictValue;
	}

	public String getLastValue() {
		return lastValue;
	}

	public void setLastValue(String lastValue) {
		this.lastValue = lastValue;
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

	@Override
	public String toString() {
		return "Calendar [name=" + name + ", country=" + country + ", id="
				+ basicIndexId + ", period=" + period + ", importance="
				+ importance + ", predictValue=" + predictValue
				+ ", lastvalue=" + lastValue + ", value=" + value + ", year="
				+ year + ", positiveItem=" + positiveItem + ", negativeItem="
				+ negativeItem + ", level=" + level + ", url=" + url
				+ ", date=" + date + ", time=" + time + "]";
	}

	@Override
	public int compareTo(CalendarEntity another) {
		// TODO Auto-generated method stub

		return this.getTime().compareTo(another.getTime());
	}

}
