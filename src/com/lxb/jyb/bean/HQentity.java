package com.lxb.jyb.bean;

import android.os.Parcel;
import android.os.Parcelable;

public class HQentity implements Parcelable {

	private String code;
	private String name;
	private int baoliu;
	private boolean isCheck;

	public boolean getIsCheck() {
		return isCheck;
	}

	public void setCheck(boolean isCheck) {
		this.isCheck = isCheck;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getBaoliu() {
		return baoliu;
	}

	public void setBaoliu(int baoliu) {
		this.baoliu = baoliu;
	}

	@Override
	public String toString() {
		return "HQentity [code=" + code + ", name=" + name + ", baoliu="
				+ baoliu + "]";
	}

	public HQentity() {
		super();
		// TODO Auto-generated constructor stub
	}

	public HQentity(String code, String name, int baoliu) {
		super();
		this.code = code;
		this.name = name;
		this.baoliu = baoliu;
	}

	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		// TODO Auto-generated method stub
		dest.writeString(code);
		dest.writeString(name);
		dest.writeInt(baoliu);
	}

	public static final Parcelable.Creator<HQentity> CREATOR = new Creator<HQentity>() {

		@Override
		public HQentity[] newArray(int size) {
			// TODO Auto-generated method stub
			return new HQentity[size];
		}

		@Override
		public HQentity createFromParcel(Parcel source) {
			// TODO Auto-generated method stub
			HQentity qentity = new HQentity();
			qentity.setCode(source.readString());
			qentity.setName(source.readString());
			qentity.setBaoliu(source.readInt());
			return qentity;
		}
	};
}
