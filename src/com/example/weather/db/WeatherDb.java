package com.example.weather.db;

import java.util.List;

public class WeatherDb {

	protected final static String DBNAME="weatherdb.db";
	protected final static int VERSION=1;
	
	
	
	public void saveProvince(Province province){
		
	}
	
	public void saveCity(City city){
		
	}
	
	public void saveCounty(County county){
		
	}
	
	public List<Province> loadProvince(){
		List<Province> provinces = null;
		
		
		
		return provinces;
	}
	
	public List<City> loadCity(int pid){
		List<City> citys=null;
		return citys;
	}
	
	public List<County> loadCounty(int cid){
		List<County> countys=null;
		return countys;
	}
}
