package com.lxb.jyb.bean;

public class XYchicangBean {
	private String ordercode;
	private String boml;
	private String zuoduo;
	private String num;
	private String zhisun;
	private String zhiying;
	private String rucang;
	private Double xianjia;
	private Double price;

	
	public XYchicangBean(String ordercode, String boml, String zuoduo,
			String num, String zhisun, String zhiying, String rucang,
			Double xianjia, Double price) {
		super();
		this.ordercode = ordercode;
		this.boml = boml;
		this.zuoduo = zuoduo;
		this.num = num;
		this.zhisun = zhisun;
		this.zhiying = zhiying;
		this.rucang = rucang;
		this.xianjia = xianjia;
		this.price = price;
	}


	public String getOrdercode() {
		return ordercode;
	}


	public void setOrdercode(String ordercode) {
		this.ordercode = ordercode;
	}


	public String getBoml() {
		return boml;
	}


	public void setBoml(String boml) {
		this.boml = boml;
	}


	public String getZuoduo() {
		return zuoduo;
	}


	public void setZuoduo(String zuoduo) {
		this.zuoduo = zuoduo;
	}


	public String getNum() {
		return num;
	}


	public void setNum(String num) {
		this.num = num;
	}


	public String getZhisun() {
		return zhisun;
	}


	public void setZhisun(String zhisun) {
		this.zhisun = zhisun;
	}


	public String getZhiying() {
		return zhiying;
	}


	public void setZhiying(String zhiying) {
		this.zhiying = zhiying;
	}


	public String getRucang() {
		return rucang;
	}


	public void setRucang(String rucang) {
		this.rucang = rucang;
	}


	public Double getXianjia() {
		return xianjia;
	}


	public void setXianjia(Double xianjia) {
		this.xianjia = xianjia;
	}


	public Double getPrice() {
		return price;
	}


	public void setPrice(Double price) {
		this.price = price;
	}

}
