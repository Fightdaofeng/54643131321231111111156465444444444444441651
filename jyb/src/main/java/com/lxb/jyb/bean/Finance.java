package com.lxb.jyb.bean;

import org.json.JSONObject;

import android.os.Parcel;
import android.os.Parcelable;

public class Finance implements Comparable<Finance> {

	/** 事件状态 */
	private String eventStatus;
	/** 事件种类 */
	private String eventType;
	/** 事件国家 */
	private String eventCountry;
	/** 事件地区 */
	private String eventRegion;
	/** 事件重要性 */
	private String eventImportance;
	/** 事件内容 */
	private String eventContent;
	/** 事件标题 */
	private String eventTitle;
	/** 事件连接 */
	private String eventLink;
	/** 事件日期 */
	private String eventDate;
	/** 事件事件 */
	private String eventTime;

	public Finance(JSONObject obj) {
		if (obj != null) {
			eventStatus = obj.isNull("eventStatus") ? "" : obj
					.optString("eventStatus");
			eventType = obj.isNull("eventType") ? "" : obj
					.optString("eventType");
			eventCountry = obj.isNull("eventCountry") ? "" : obj
					.optString("eventCountry");
			eventRegion = obj.isNull("eventRegion") ? "" : obj
					.optString("eventRegion");
			eventImportance = obj.isNull("eventImportance") ? "" : obj
					.optString("eventImportance");
			eventContent = obj.isNull("eventContent") ? "" : obj
					.optString("eventContent");
			eventTitle = obj.isNull("eventTitle") ? "" : obj
					.optString("eventTitle");
			eventLink = obj.isNull("eventLink") ? "" : obj
					.optString("eventLink");
			eventDate = obj.isNull("eventDate") ? "" : obj
					.optString("eventDate");
			eventTime = obj.isNull("eventTime") ? "" : obj
					.optString("eventTime");
		}

	}

	public Finance(String eventStatus, String eventType, String eventCountry,
			String eventRegion, String eventImportance, String eventContent,
			String eventTitle, String eventLink, String eventDate,
			String eventTime) {
		super();
		this.eventStatus = eventStatus;
		this.eventType = eventType;
		this.eventCountry = eventCountry;
		this.eventRegion = eventRegion;
		this.eventImportance = eventImportance;
		this.eventContent = eventContent;
		this.eventTitle = eventTitle;
		this.eventLink = eventLink;
		this.eventDate = eventDate;
		this.eventTime = eventTime;
	}

	public Finance() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getEventStatus() {
		return eventStatus;
	}

	public void setEventStatus(String eventStatus) {
		this.eventStatus = eventStatus;
	}

	public String getEventType() {
		return eventType;
	}

	public void setEventType(String eventType) {
		this.eventType = eventType;
	}

	public String getEventCountry() {
		return eventCountry;
	}

	public void setEventCountry(String eventCountry) {
		this.eventCountry = eventCountry;
	}

	public String getEventRegion() {
		return eventRegion;
	}

	public void setEventRegion(String eventRegion) {
		this.eventRegion = eventRegion;
	}

	public String getEventImportance() {
		return eventImportance;
	}

	public void setEventImportance(String eventImportance) {
		this.eventImportance = eventImportance;
	}

	public String getEventContent() {
		return eventContent;
	}

	public void setEventContent(String eventContent) {
		this.eventContent = eventContent;
	}

	public String getEventTitle() {
		return eventTitle;
	}

	public void setEventTitle(String eventTitle) {
		this.eventTitle = eventTitle;
	}

	public String getEventLink() {
		return eventLink;
	}

	public void setEventLink(String eventLink) {
		this.eventLink = eventLink;
	}

	public String getEventDate() {
		return eventDate;
	}

	public void setEventDate(String eventDate) {
		this.eventDate = eventDate;
	}

	public String getEventTime() {
		return eventTime;
	}

	public void setEventTime(String eventTime) {
		this.eventTime = eventTime;
	}

	@Override
	public String toString() {
		return "Finance [eventStatus=" + eventStatus + ", eventType="
				+ eventType + ", eventCountry=" + eventCountry
				+ ", eventRegion=" + eventRegion + ", eventImportance="
				+ eventImportance + ", eventContent=" + eventContent
				+ ", eventTitle=" + eventTitle + ", eventLink=" + eventLink
				+ ", eventDate=" + eventDate + ", eventTime=" + eventTime + "]";
	}

	@Override
	public int compareTo(Finance another) {
		// TODO Auto-generated method stub
		return this.getEventTime().compareTo(another.getEventTime());
	}

}
