package com.lxb.jyb.bean;

import android.os.Parcel;
import android.os.Parcelable.Creator;

public class CalSXItem {

	private String name;
	private boolean isCheck;

	public boolean getIsCheck() {
		return isCheck;
	}

	public void setCheck(boolean isCheck) {
		this.isCheck = isCheck;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "HQentity [ name=" + name + "]";
	}

	public CalSXItem() {
		super();
		// TODO Auto-generated constructor stub
	}

	public CalSXItem(String name) {
		super();
		this.name = name;
	}

	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	public void writeToParcel(Parcel dest, int flags) {
		// TODO Auto-generated method stub
		dest.writeString(name);
	}

	public static final Creator<CalSXItem> CREATOR = new Creator<CalSXItem>() {

		@Override
		public CalSXItem[] newArray(int size) {
			// TODO Auto-generated method stub
			return new CalSXItem[size];
		}

		@Override
		public CalSXItem createFromParcel(Parcel source) {
			// TODO Auto-generated method stub
			CalSXItem qentity = new CalSXItem();
			qentity.setName(source.readString());
			return qentity;
		}

	};
}
