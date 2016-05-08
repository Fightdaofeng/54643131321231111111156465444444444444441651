package com.lxb.jyb.push;

public class Tip {
	private String tipName;
	public String id;
	public String value;
	public String time;
	private String period;
	private String country;
	private String name;
	private String predictValue;
	private String lastValue;

	public String toJson() {
		StringBuffer buffer = new StringBuffer();
		buffer.append("[{");
		buffer.append("\"tipName\"").append(":\"").append(tipName)
				.append("\",");
		buffer.append("\"id\"").append(":\"").append(id).append("\",");
		buffer.append("\"value\"").append(":\"").append(value).append("\",");
		buffer.append("\"time\"").append(":\"").append(time).append("\",");
		buffer.append("\"period\"").append(":\"").append(period).append("\",");
		buffer.append("\"country\"").append(":\"").append(country)
				.append("\",");
		buffer.append("\"name\"").append(":\"").append(name).append("\",");
		buffer.append("\"predictValue\"").append(":\"").append(predictValue)
				.append("\",");
		buffer.append("\"lastValue\"").append(":\"").append(lastValue)
				.append("\"");
		buffer.append("}]");

		return buffer.toString();
	}

	public Tip(String tipName, String id, String value, String time,
			String period, String country, String name, String predictValue,
			String lastValue) {
		super();
		this.tipName = tipName;
		this.id = id;
		this.value = value;
		this.time = time;
		this.period = period;
		this.country = country;
		this.name = name;
		this.predictValue = predictValue;
		this.lastValue = lastValue;
	}

	public Tip() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getTipName() {
		return tipName;
	}

	public void setTipName(String tipName) {
		this.tipName = tipName;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
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

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPredictValue() {
		return predictValue;
	}

	public void setPredictValue(String predictValue) {
		this.predictValue = predictValue;
	}

	public String getLastValue() {
		return lastValue;
	}

	public void setLastValue(String lastValue) {
		this.lastValue = lastValue;
	}

	@Override
	public String toString() {
		return "Tip [tipName=" + tipName + ", id=" + id + ", value=" + value
				+ ", time=" + time + ",  period=" + period + ", country="
				+ country + ", name=" + name + ", predictValue=" + predictValue
				+ ", lastValue=" + lastValue + "]";
	}

}
