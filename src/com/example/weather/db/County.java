package com.example.weather.db;

public class County {
	protected int id=0;
	protected int cid=0;
	protected String name ="";
	protected String code="";
	
	public int getId(){
		return this.id;
	}
	
	public int getCid(){
		return this.cid;
	}
	
	public String getName(){
		return this.name;
	}
	
	public String getCode(){
		return this.code;
	}
	
	public void setId(int id){
		this.id=id;
	}
	
	public void setCid(int cid){
		this.cid=cid;
	}
	public void setName(String name){
		this.name=name;
	}
	public void setCode(String code){
		this.code=code;
	}
	
	
	
}
