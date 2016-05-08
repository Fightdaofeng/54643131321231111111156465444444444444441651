package com.lxb.jyb.bean;


import org.json.JSONObject;

public class UserInfoBean {
	/**账户名*/
	private String name;
	/**交易货币*/
	private String currency;
	/**用户余额*/
	private String balance;
	/**账户保证金*/
	private String equiety;
	/**账户可用保证金*/
	private String freemargin;
	/**已用保证金*/
	private String margin;
	/**杠杆效率*/
	private String leverage;
	/**盈利*/
	private String profit;

	public UserInfoBean(JSONObject obj) {
		if (null != obj) {
			name = obj.isNull("name") ? "" : obj.optString("name");
			currency = obj.isNull("currency") ? "" : obj.optString("currency");
			balance = obj.isNull("balance") ? "" : obj.optString("balance");
			equiety = obj.isNull("equiety") ? "" : obj.optString("equiety");
			freemargin = obj.isNull("freemargin") ? "" : obj.optString("freemargin");
			margin = obj.isNull("margin") ? "" : obj.optString("margin");
			leverage = obj.isNull("leverage") ? "" : obj.optString("leverage");
			profit = obj.isNull("profit") ? "" : obj.optString("profit");
		}
	}

	public UserInfoBean() {
		super();
		// TODO Auto-generated constructor stub
	}

	public UserInfoBean(String name, String currency, String balance,
			String equiety, String freemargin, String margin, String leverage,
			String profit) {
		super();
		this.name = name;
		this.currency = currency;
		this.balance = balance;
		this.equiety = equiety;
		this.freemargin = freemargin;
		this.margin = margin;
		this.leverage = leverage;
		this.profit = profit;
	}

	@Override
	public String toString() {
		return "UserInfoBean [name=" + name + ", currency=" + currency
				+ ", balance=" + balance + ", equiety=" + equiety
				+ ", freemargin=" + freemargin + ", margin=" + margin
				+ ", leverage=" + leverage + ", profit=" + profit + "]";
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public String getBalance() {
		return balance;
	}

	public void setBalance(String balance) {
		this.balance = balance;
	}

	public String getEquiety() {
		return equiety;
	}

	public void setEquiety(String equiety) {
		this.equiety = equiety;
	}

	public String getFreemargin() {
		return freemargin;
	}

	public void setFreemargin(String freemargin) {
		this.freemargin = freemargin;
	}

	public String getMargin() {
		return margin;
	}

	public void setMargin(String margin) {
		this.margin = margin;
	}

	public String getLeverage() {
		return leverage;
	}

	public void setLeverage(String leverage) {
		this.leverage = leverage;
	}

	public String getProfit() {
		return profit;
	}

	public void setProfit(String profit) {
		this.profit = profit;
	}

}
