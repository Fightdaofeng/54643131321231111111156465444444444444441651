package com.lxb.jyb.bean;

import org.json.JSONObject;

public class NewstDatePoint {

	private String lastValue;
	private String value;
	private String preValue;
	private String publishTime;
	private String positiveItem;
	private String negativeItem;

	public NewstDatePoint(JSONObject obj) {
		lastValue = obj.isNull("lastValue") ? "" : obj.optString("lastValue");
		value = obj.isNull("value") ? "" : obj.optString("value");
		preValue = obj.isNull("preValue") ? "" : obj.optString("preValue");
		publishTime = obj.isNull("publishTime") ? "" : obj
				.optString("publishTime");
		positiveItem = obj.isNull("positiveItem") ? "" : obj
				.optString("positiveItem");
		negativeItem = obj.isNull("negativeItem") ? "" : obj
				.optString("negativeItem");
	}

	@Override
	public String toString() {
		return "NewstDatePoint [lastValue=" + lastValue + ", value=" + value
				+ ", preValue=" + preValue + ", publishTime=" + publishTime
				+ ", positiveItem=" + positiveItem + ", negativeItem="
				+ negativeItem + "]";
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

	public String getPreValue() {
		return preValue;
	}

	public void setPreValue(String preValue) {
		this.preValue = preValue;
	}

	public String getPublishTime() {
		return publishTime;
	}

	public void setPublishTime(String publishTime) {
		this.publishTime = publishTime;
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

	public NewstDatePoint() {
		super();
		// TODO Auto-generated constructor stub
	}

	public NewstDatePoint(String lastValue, String value, String preValue,
			String publishTime, String positiveItem, String negativeItem) {
		super();
		this.lastValue = lastValue;
		this.value = value;
		this.preValue = preValue;
		this.publishTime = publishTime;
		this.positiveItem = positiveItem;
		this.negativeItem = negativeItem;
	}

}
