package com.lxb.jyb.bean;

public class OrderDataBean {
	/** 账户 */
	private String account;
	/** broker */
	private String broker;
	/** symbol */
	private String symbol;
	/** 距离 */
	private String lots;
	/** 方式 */
	private String ordertype;
	/** 止盈 */
	private String sl;
	/** 止损 */
	private String tp;
	/** comment */
	private String comment;
	/** price */
	private String price;
	/** expiretime */
	private String expiretime;

	public StringBuffer toJSON() {
		StringBuffer buffer = new StringBuffer();
		buffer.append("{");
		buffer.append("\"account\"").append(":\"").append(account)
				.append("\",");

		buffer.append("\"broker\"").append(":\"").append(broker).append("\",");
		buffer.append("\"symbol\"").append(":\"").append(symbol).append("\",");
		buffer.append("\"lots\"").append(":\"").append(lots).append("\",");
		buffer.append("\"ordertype\"").append(":\"").append(ordertype)
				.append("\",");
		buffer.append("\"sl\"").append(":\"").append(sl).append("\",");
		buffer.append("\"tp\"").append(":\"").append(tp).append("\",");
		buffer.append("\"comment\"").append(":\"").append(comment)
				.append("\",");
		buffer.append("\"price\"").append(":\"").append(price).append("\",");
		buffer.append("\"expiretime\"").append(":\"").append(expiretime)
				.append("\"");
		buffer.append("}");

		return buffer;
	}

	public OrderDataBean() {
		super();
		// TODO Auto-generated constructor stub
	}

	public OrderDataBean(String account, String broker, String symbol,
			String lots, String ordertype, String sl, String tp,
			String comment, String price, String expiretime) {
		super();
		this.account = account;
		this.broker = broker;
		this.symbol = symbol;
		this.lots = lots;
		this.ordertype = ordertype;
		this.sl = sl;
		this.tp = tp;
		this.comment = comment;
		this.price = price;
		this.expiretime = expiretime;
	}

	@Override
	public String toString() {
		return "OrderDataBean [account=" + account + ", broker=" + broker
				+ ", symbol=" + symbol + ", lots=" + lots + ", ordertype="
				+ ordertype + ", sl=" + sl + ", tp=" + tp + ", comment="
				+ comment + ", price=" + price + ", expiretime=" + expiretime
				+ "]";
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getBroker() {
		return broker;
	}

	public void setBroker(String broker) {
		this.broker = broker;
	}

	public String getSymbol() {
		return symbol;
	}

	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}

	public String getLots() {
		return lots;
	}

	public void setLots(String lots) {
		this.lots = lots;
	}

	public String getOrdertype() {
		return ordertype;
	}

	public void setOrdertype(String ordertype) {
		this.ordertype = ordertype;
	}

	public String getSl() {
		return sl;
	}

	public void setSl(String sl) {
		this.sl = sl;
	}

	public String getTp() {
		return tp;
	}

	public void setTp(String tp) {
		this.tp = tp;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public String getExpiretime() {
		return expiretime;
	}

	public void setExpiretime(String expiretime) {
		this.expiretime = expiretime;
	}

}
