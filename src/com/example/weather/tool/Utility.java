package com.example.weather.tool;

import android.text.TextUtils;
import android.util.Log;

import com.example.weather.db.*;

public class Utility {
	
	public static Boolean handleProvince(WeatherDb db,String data){
		if(TextUtils.isEmpty(data))
			return false;
		//�����е�ʡ�ݷָ����
		String allProvince[]=data.split(",");
		if(allProvince==null||allProvince.length<1)
			return false;
		//ѭ���������е�ʡ��
		for (String provinceStr : allProvince) {
			//�ָ����ֺʹ���
			String temp[]=provinceStr.split("\\|");
			Province province =new Province();
			province.setName(temp[1]);
			province.setCode(temp[0]);
			//��ʡ�������ݿ�
			db.saveProvince(province);
		}
		return true;
	}
	
	public static Boolean handleCity(WeatherDb db,String data,int pId){
		if(TextUtils.isEmpty(data))
			return false;
		
		String allCity[]=data.split(",");
		if(allCity==null||allCity.length<1)
			return false;
		
		for(String cityStr : allCity){
			String temp[]=cityStr.split("\\|");
			City city=new City();
			city.setCode(temp[0]);
			city.setName(temp[1]);
			city.setpId(pId);
			db.saveCity(city);
		}
		
		return true;
	}
	
	public static Boolean handleCounty(WeatherDb db,String data,int cId){
		if(TextUtils.isEmpty(data))
			return false;
		
		String allCounty[]=data.split(",");
		if(allCounty==null||allCounty.length<1)
			return false;
		
		for(String cityStr : allCounty){
			String temp[]=cityStr.split("\\|");
			County county=new County();
			county.setCid(cId);
			county.setCode(temp[0]);
			county.setName(temp[1]);
			db.saveCounty(county);
		}
		
		return true;
		
	}
	
}
