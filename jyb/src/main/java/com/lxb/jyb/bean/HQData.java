package com.lxb.jyb.bean;

import org.json.JSONObject;

import android.os.Parcel;
import android.os.Parcelable;

public class HQData implements Parcelable {
	private double change;
	private double changePercent;
	private double close;
	private String code;
	private double hight;
	private double low;
	private String name;
	private double newPrice;
	private double open;
	private long time;
	private int baoliu;
	private String gflags;

	public String toJson() {
		StringBuffer buffer = new StringBuffer();
		buffer.append("[{");
		buffer.append("\"change\"").append(":").append(change).append(",");
		buffer.append("\"changePercent\"").append(":").append(changePercent)
				.append(",");
		buffer.append("\"close\"").append(":").append(close).append(",");
		buffer.append("\"code\"").append(":\"").append(code).append("\",");
		buffer.append("\"hight\"").append(":").append(hight).append(",");
		buffer.append("\"low\"").append(":").append(low).append(",");
		buffer.append("\"name\"").append(":\"").append(name).append("\",");
		buffer.append("\"newPrice\"").append(":").append(newPrice).append(",");
		buffer.append("\"open\"").append(":").append(open).append(",");
		buffer.append("\"time\"").append(":").append(time).append(",");
		buffer.append("\"baoliu\"").append(":").append(baoliu).append(",");
		buffer.append("\"gflags\"").append(":\"").append(gflags)
				.append("\"");
		buffer.append("}]");

		return buffer.toString();

	}

	public String getGflags() {
		return gflags;
	}

	public void setGflags(String gflags) {
		this.gflags = gflags;
	}

	public HQData(JSONObject obj, int baoliu, String name) {
		if (obj != null) {
			change = obj.isNull("change") ? 0 : obj.optDouble("change");
			changePercent = obj.isNull("changePercent") ? 0 : obj
					.optDouble("changePercent");
			close = obj.isNull("close") ? 0 : obj.optDouble("close");
			code = obj.isNull("code") ? "0" : obj.optString("code");
			hight = obj.isNull("high") ? 0 : obj.optDouble("high");
			low = obj.isNull("low") ? 0 : obj.optDouble("low");
			this.name = obj.isNull("name") ? name : name;
			newPrice = obj.isNull("newPrice") ? 0 : obj.optDouble("newPrice");
			open = obj.isNull("open") ? 0 : obj.optDouble("open");
			time = obj.isNull("time") ? 0 : obj.optLong("time");
			this.baoliu = baoliu;
		}

	}

	public int getBaoliu() {
		return baoliu;
	}

	public void setBaoliu(int baoliu) {
		this.baoliu = baoliu;
	}

	public double getChange() {
		return change;
	}

	public void setChange(double change) {
		this.change = change;
	}

	public double getChangePercent() {
		return changePercent;
	}

	public void setChangePercent(double changePercent) {
		this.changePercent = changePercent;
	}

	public double getClose() {
		return close;
	}

	public void setClose(double d) {
		this.close = d;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public double getHight() {
		return hight;
	}

	public void setHight(double d) {
		this.hight = d;
	}

	public double getLow() {
		return low;
	}

	public void setLow(double low) {
		this.low = low;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getNewPrice() {
		return newPrice;
	}

	public void setNewPrice(double newPrice) {
		this.newPrice = newPrice;
	}

	public double getOpen() {
		return open;
	}

	public void setOpen(double open) {
		this.open = open;
	}

	public long getTime() {
		return time;
	}

	public void setTime(long time) {
		this.time = time;
	}

	public HQData() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		// TODO Auto-generated method stub
		dest.writeDouble(change);
		dest.writeDouble(changePercent);
		dest.writeDouble(close);
		dest.writeString(code);
		dest.writeDouble(hight);
		dest.writeDouble(low);
		dest.writeString(name);
		dest.writeDouble(newPrice);
		dest.writeDouble(open);
		dest.writeLong(time);
		dest.writeInt(baoliu);
		dest.writeString(gflags);
	}

	public static final Parcelable.Creator<HQData> CREATOR = new Creator<HQData>() {

		@Override
		public HQData[] newArray(int size) {
			// TODO Auto-generated method stub
			return new HQData[size];
		}

		@Override
		public HQData createFromParcel(Parcel source) {
			// TODO Auto-generated method stub
			HQData data = new HQData();
			data.setChange(source.readDouble());
			data.setChangePercent(source.readDouble());
			data.setClose(source.readDouble());
			data.setCode(source.readString());
			data.setHight(source.readDouble());
			data.setLow(source.readDouble());
			data.setName(source.readString());
			data.setNewPrice(source.readDouble());
			data.setOpen(source.readDouble());
			data.setTime(source.readLong());
			data.setBaoliu(source.readInt());
			data.setGflags(source.readString());
			return data;
		}
	};

	public HQData(double change, double changePercent, double close,
			String code, double hight, double low, String name,
			double newPrice, double open, long time, int baoliu) {
		super();
		this.change = change;
		this.changePercent = changePercent;
		this.close = close;
		this.code = code;
		this.hight = hight;
		this.low = low;
		this.name = name;
		this.newPrice = newPrice;
		this.open = open;
		this.time = time;
		this.baoliu = baoliu;
	}

	@Override
	public String toString() {
		return "HQData [change=" + change + ", changePercent=" + changePercent
				+ ", close=" + close + ", code=" + code + ", hight=" + hight
				+ ", low=" + low + ", name=" + name + ", newPrice=" + newPrice
				+ ", open=" + open + ", time=" + time + "]";
	}

}
