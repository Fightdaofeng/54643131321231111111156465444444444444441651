package com.lxb.jyb.bean;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Newsflash {
	private String liveTitle;
	private String liveText;
	private String liveImportance;
	private String liveDate;
	private String liveTime;
	private ArrayList<String> tag;

	public Newsflash(JSONObject obj) {
		liveTitle = obj.isNull("liveTitle") ? "" : obj.optString("liveTitle");
		liveText = obj.isNull("liveText") ? "" : obj.optString("liveText");
		liveImportance = obj.isNull("liveImportance") ? "" : obj
				.optString("liveImportance");
		liveDate = obj.isNull("liveDate") ? "" : obj.optString("liveDate");
		liveTime = obj.isNull("liveTime") ? "" : obj.optString("liveTime");

		JSONArray jsonArray = obj.optJSONArray("tag");
		tag = new ArrayList<String>();
		for (int i = 0; i < jsonArray.length(); i++) {
			JSONObject object;
			try {
				object = (JSONObject) jsonArray.get(i);
				String optString = object.optString("tagName");
				tag.add(optString);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
	}

	public String getLiveTitle() {
		return liveTitle;
	}

	public void setLiveTitle(String liveTitle) {
		this.liveTitle = liveTitle;
	}

	public String getLiveText() {
		return liveText;
	}

	public void setLiveText(String liveText) {
		this.liveText = liveText;
	}

	public String getLiveImportance() {
		return liveImportance;
	}

	public void setLiveImportance(String liveImportance) {
		this.liveImportance = liveImportance;
	}

	public String getLiveDate() {
		return liveDate;
	}

	public void setLiveDate(String liveDate) {
		this.liveDate = liveDate;
	}

	public String getLiveTime() {
		return liveTime;
	}

	public void setLiveTime(String liveTime) {
		this.liveTime = liveTime;
	}

	public ArrayList<String> getTag() {
		return tag;
	}

	public void setTag(ArrayList<String> tag) {
		this.tag = tag;
	}

	public Newsflash() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Newsflash(String liveTitle, String liveText, String liveImportance,
			String liveDate, String liveTime, ArrayList<String> tag) {
		super();
		this.liveTitle = liveTitle;
		this.liveText = liveText;
		this.liveImportance = liveImportance;
		this.liveDate = liveDate;
		this.liveTime = liveTime;
		this.tag = tag;
	}

	@Override
	public String toString() {
		return "Newsflash [liveTitle=" + liveTitle + ", liveText=" + liveText
				+ ", liveImportance=" + liveImportance + ", liveDate="
				+ liveDate + ", liveTime=" + liveTime + ", tag=" + tag + "]";
	}

}
