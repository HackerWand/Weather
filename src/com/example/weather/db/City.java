package com.example.weather.db;

public class City {
	protected int id=0;
	protected String name="";
	protected String code="";
	protected int pId=0;
	public int getId() {
		return id;
	}
	public String getName() {
		return name;
	}
	public String getCode() {
		return code;
	}
	public int getpId() {
		return pId;
	}
	public void setId(int id) {
		this.id = id;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public void setpId(int pId) {
		this.pId = pId;
	}
	
}
