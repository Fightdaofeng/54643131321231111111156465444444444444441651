package com.lxb.jyb.bean;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONObject;

public class OrderSelect implements Comparable<OrderSelect>,Parcelable {
	/** 备注 */
	private String comment;
	/** 回扣 */
	private String commssion;
	/** 数量 */
	private String lots;
	/** magic */
	private String magic;
	/** 入仓价 */
	private String openprice;
	/** 入仓时间 */
	private String opentime;
	/** 入仓类型 买或卖 */
	private String ordertype;
	/** 盈利 */
	private String profit;
	/** 止损 */
	private String sl;
	/** swap */
	private String swap;
	/** 货币兑 */
	private String symbol;
	/** 订单号 */
	private String ticket;
	/** 止盈 */
	private String tp;

	public OrderSelect(JSONObject obj) {
		if (obj != null) {
			comment = obj.isNull("comment") ? "" : obj.optString("comment");
			commssion = obj.isNull("commssion") ? "" : obj
					.optString("commssion");
			lots = obj.isNull("lots") ? "" : obj.optString("lots");
			magic = obj.isNull("magic") ? "" : obj.optString("magic");
			openprice = obj.isNull("openprice") ? "" : obj
					.optString("openprice");
			opentime = obj.isNull("opentime") ? "" : obj.optString("opentime");
			ordertype = obj.isNull("ordertype") ? "" : obj
					.optString("ordertype");
			profit = obj.isNull("profit") ? "" : obj.optString("profit");
			sl = obj.isNull("sl") ? "" : obj.optString("sl");
			swap = obj.isNull("swap") ? "" : obj.optString("swap");
			symbol = obj.isNull("symbol") ? "" : obj.optString("symbol");
			ticket = obj.isNull("ticket") ? "" : obj.optString("ticket");
			tp = obj.isNull("tp") ? "" : obj.optString("tp");
		}
	}

	public OrderSelect() {
		super();
		// TODO Auto-generated constructor stub
	}

	public OrderSelect(String comment, String commssion, String lots,
			String magic, String openprice, String opentime, String ordertype,
			String profit, String sl, String swap, String symbol,
			String ticket, String tp) {
		super();
		this.comment = comment;
		this.commssion = commssion;
		this.lots = lots;
		this.magic = magic;
		this.openprice = openprice;
		this.opentime = opentime;
		this.ordertype = ordertype;
		this.profit = profit;
		this.sl = sl;
		this.swap = swap;
		this.symbol = symbol;
		this.ticket = ticket;
		this.tp = tp;
	}

	public String getTicket() {
		return ticket;
	}

	public void setTicket(String ticket) {
		this.ticket = ticket;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getCommssion() {
		return commssion;
	}

	public void setCommssion(String commssion) {
		this.commssion = commssion;
	}

	public String getLots() {
		return lots;
	}

	public void setLots(String lots) {
		this.lots = lots;
	}

	public String getMagic() {
		return magic;
	}

	public void setMagic(String magic) {
		this.magic = magic;
	}

	public String getOpenprice() {
		return openprice;
	}

	public void setOpenprice(String openprice) {
		this.openprice = openprice;
	}

	public String getOpentime() {
		return opentime;
	}

	public void setOpentime(String opentime) {
		this.opentime = opentime;
	}

	public String getOrdertype() {
		return ordertype;
	}

	public void setOrdertype(String ordertype) {
		this.ordertype = ordertype;
	}

	public String getProfit() {
		return profit;
	}

	public void setProfit(String profit) {
		this.profit = profit;
	}

	public String getSl() {
		return sl;
	}

	public void setSl(String sl) {
		this.sl = sl;
	}

	public String getSwap() {
		return swap;
	}

	public void setSwap(String swap) {
		this.swap = swap;
	}

	public String getSymbol() {
		return symbol;
	}

	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}

	public String getTp() {
		return tp;
	}

	public void setTp(String tp) {
		this.tp = tp;
	}


	@Override
	public int compareTo(OrderSelect another) {
		// TODO Auto-generated method stub
		return another.getOpentime().compareTo(this.getOpentime());
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(this.comment);
		dest.writeString(this.commssion);
		dest.writeString(this.lots);
		dest.writeString(this.magic);
		dest.writeString(this.openprice);
		dest.writeString(this.opentime);
		dest.writeString(this.ordertype);
		dest.writeString(this.profit);
		dest.writeString(this.sl);
		dest.writeString(this.swap);
		dest.writeString(this.symbol);
		dest.writeString(this.ticket);
		dest.writeString(this.tp);
	}

	protected OrderSelect(Parcel in) {
		this.comment = in.readString();
		this.commssion = in.readString();
		this.lots = in.readString();
		this.magic = in.readString();
		this.openprice = in.readString();
		this.opentime = in.readString();
		this.ordertype = in.readString();
		this.profit = in.readString();
		this.sl = in.readString();
		this.swap = in.readString();
		this.symbol = in.readString();
		this.ticket = in.readString();
		this.tp = in.readString();
	}

	public static final Parcelable.Creator<OrderSelect> CREATOR = new Parcelable.Creator<OrderSelect>() {
		public OrderSelect createFromParcel(Parcel source) {
			return new OrderSelect(source);
		}

		public OrderSelect[] newArray(int size) {
			return new OrderSelect[size];
		}
	};
}
