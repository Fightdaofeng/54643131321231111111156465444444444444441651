package com.lxb.jyb.bean;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

public class CalDetails {
	private String name;
	private String country;
	private String unit;
	private String interpretation;
	private String publishOrganization;
	private String publishFrequncy;
	private String statisticMethod;
	private String explanation;
	private String period;
	private String importance;

	private ArrayList<DataPoint> dataPoint;
	private String nextpublishTime;
	private ArrayList<NewstDatePoint> newstDatePoint;

	public CalDetails(JSONObject obj) {
		if (obj != null) {
			name = obj.isNull("name") ? "" : obj.optString("name");
			country = obj.isNull("country") ? "" : obj.optString("country");
			unit = obj.isNull("unit") ? "" : obj.optString("unit");
			interpretation = obj.isNull("interpretation") ? "" : obj
					.optString("interpretation");
			publishOrganization = obj.isNull("publishOrganization") ? "" : obj
					.optString("publishOrganization");
			publishFrequncy = obj.isNull("publishFrequncy") ? "" : obj
					.optString("publishFrequncy");
			statisticMethod = obj.isNull("statisticMethod") ? "" : obj
					.optString("statisticMethod");
			explanation = obj.isNull("explanation") ? "" : obj
					.optString("explanation");
			nextpublishTime = obj.isNull("nextpublishTime") ? "" : obj
					.optString("nextpublishTime");
			period = obj.isNull("period") ? "" : obj.optString("period");
			importance = obj.isNull("importance") ? "" : obj
					.optString("importance");

			newstDatePoint = new ArrayList<NewstDatePoint>();
			JSONObject optJSONObject = obj.optJSONObject("newestDataPoint");
			NewstDatePoint datePoint = new NewstDatePoint(optJSONObject);
			newstDatePoint.add(datePoint);

			JSONArray jsonArray = obj.optJSONArray("dataPoint");
			dataPoint = new ArrayList<DataPoint>();
			for (int i = 0; i < jsonArray.length(); i++) {
				DataPoint point = new DataPoint(jsonArray.optJSONObject(i));
				dataPoint.add(point);
			}
		}
	}

	public String getPeriod() {
		return period;
	}

	public void setPeriod(String period) {
		this.period = period;
	}

	public ArrayList<NewstDatePoint> getNewstDatePoint() {
		return newstDatePoint;
	}

	public void setNewstDatePoint(ArrayList<NewstDatePoint> newstDatePoint) {
		this.newstDatePoint = newstDatePoint;
	}

	public CalDetails(String name, String country, String unit,
			String interpretation, String publishOrganization,
			String publishFrequncy, String statisticMethod, String explanation,
			ArrayList<DataPoint> dataPoint, String nextpublishTime,

			ArrayList<NewstDatePoint> newstDatePoint) {
		super();
		this.name = name;
		this.country = country;
		this.unit = unit;
		this.interpretation = interpretation;
		this.publishOrganization = publishOrganization;
		this.publishFrequncy = publishFrequncy;
		this.statisticMethod = statisticMethod;
		this.explanation = explanation;
		this.dataPoint = dataPoint;
		this.nextpublishTime = nextpublishTime;
		this.newstDatePoint = newstDatePoint;
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

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public String getInterpretation() {
		return interpretation;
	}

	public void setInterpretation(String interpretation) {
		this.interpretation = interpretation;
	}

	public String getPublishOrganization() {
		return publishOrganization;
	}

	public void setPublishOrganization(String publishOrganization) {
		this.publishOrganization = publishOrganization;
	}

	public String getPublishFrequncy() {
		return publishFrequncy;
	}

	public void setPublishFrequncy(String publishFrequncy) {
		this.publishFrequncy = publishFrequncy;
	}

	public String getStatisticMethod() {
		return statisticMethod;
	}

	public void setStatisticMethod(String statisticMethod) {
		this.statisticMethod = statisticMethod;
	}

	public String getExplanation() {
		return explanation;
	}

	public void setExplanation(String explanation) {
		this.explanation = explanation;
	}

	public ArrayList<DataPoint> getDataPoint() {
		return dataPoint;
	}

	public void setDataPoint(ArrayList<DataPoint> dataPoint) {
		this.dataPoint = dataPoint;
	}

	public String getNextpublishTime() {
		return nextpublishTime;
	}

	public void setNextpublishTime(String nextpublishTime) {
		this.nextpublishTime = nextpublishTime;
	}

	public CalDetails() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getImportance() {
		return importance;
	}

	public void setImportance(String importance) {
		this.importance = importance;
	}

	@Override
	public String toString() {
		return "CalDetails [name=" + name + ", country=" + country + ", unit="
				+ unit + ", interpretation=" + interpretation
				+ ", publishOrganization=" + publishOrganization
				+ ", publishFrequncy=" + publishFrequncy + ", statisticMethod="
				+ statisticMethod + ", explanation=" + explanation
				+ ", period=" + period + ", importance=" + importance
				+ ", dataPoint=" + dataPoint + ", nextpublishTime="
				+ nextpublishTime + ", newstDatePoint=" + newstDatePoint + "]";
	}

}
