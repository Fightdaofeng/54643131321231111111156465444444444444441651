package com.lxb.jyb.bean;

import org.json.JSONObject;

public class DataPoint {
	private String value;
	private String date;
	private String time;
	private String period;
	
	
	public DataPoint(JSONObject obj){
		value = obj.isNull("value") ? "" : obj.optString("value");
		date = obj.isNull("Date") ? "" : obj.optString("Date");
		time = obj.isNull("Time") ? "" : obj.optString("Time");
		period = obj.isNull("period") ? "" : obj.optString("period");
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
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
	public String getPeriod() {
		return period;
	}
	public void setPeriod(String period) {
		this.period = period;
	}
	@Override
	public String toString() {
		return "DataPoint [value=" + value + ", date=" + date + ", time="
				+ time + ", period=" + period + "]";
	}
	public DataPoint() {
		super();
		// TODO Auto-generated constructor stub
	}
	public DataPoint(String value, String date, String time, String period) {
		super();
		this.value = value;
		this.date = date;
		this.time = time;
		this.period = period;
	}
	
	

}
