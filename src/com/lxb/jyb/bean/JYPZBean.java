package com.lxb.jyb.bean;

public class JYPZBean {
	private String code;
	private String name;
	private boolean isgon;

	@Override
	public String toString() {
		return "JYPZBean [code=" + code + ", name=" + name + ", isgon=" + isgon
				+ "]";
	}

	public JYPZBean() {
		super();
		// TODO Auto-generated constructor stub
	}

	public JYPZBean(String code, String name, boolean isgon) {
		super();
		this.code = code;
		this.name = name;
		this.isgon = isgon;
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

	public boolean isIsgon() {
		return isgon;
	}

	public void setIsgon(boolean isgon) {
		this.isgon = isgon;
	}

}
