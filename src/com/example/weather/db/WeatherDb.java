package com.example.weather.db;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class WeatherDb {

	
	protected SQLiteDatabase db=null;
	
	public WeatherDb(Context context){
		db=WeatherOpenHelper.getWeatherInstance(context).getWritableDatabase();
	}
	
	public void saveProvince(Province province){
		if(province==null)
			return;
		ContentValues values=new ContentValues();
		values.put("name", province.getName());
		values.put("code", province.getCode());
		db.insert("province", null, values);
	}
	
	public void saveCity(City city){
		if(city==null)
			return;
		ContentValues values=new ContentValues();
		values.put("name", city.getName());
		values.put("code",city.getCode());
		values.put("pid", city.getpId());
		db.insert("city", null, values);
	}
	
	public void saveCounty(County county){
		if(county==null)
			return;
		ContentValues values=new ContentValues();
		values.put("name", county.getName());
		values.put("code",county.getCode());
		values.put("cid",county.getCid());
		db.insert("county", null, values);
	}
	
	public List<Province> loadProvince(){
		List<Province> provinces = new ArrayList<Province>();
		
		Cursor cursor=null;
		
		cursor=db.query("province", new String[]{"id","name","code"}, null, null, null, null, null);
		if(cursor.moveToFirst()){
			do{
				Province province=new Province();
				province.setCode(cursor.getString(cursor.getColumnIndex("code")));
				province.setName(cursor.getString(cursor.getColumnIndex("name")));
				province.setId(cursor.getInt(cursor.getColumnIndex("id")));
				provinces.add(province);
			}while(cursor.moveToNext());
		}
		if(cursor!=null)
			cursor.close();
		return provinces;
	}
	
	public List<City> loadCity(int pid){
		List<City> citys=new ArrayList<City>();
		
		Cursor cursor=db.query("city", null, "pid = ?", new String []{String.valueOf(pid)}, null, null, null);
		if(cursor.moveToFirst()){
			do{
				City city=new City();
				city.setCode(cursor.getString(cursor.getColumnIndex("code")));
				city.setId(cursor.getInt(cursor.getColumnIndex("id")));
				city.setpId(cursor.getInt(cursor.getColumnIndex("pid")));
				city.setName(cursor.getString(cursor.getColumnIndex("name")));
				citys.add(city);
			}while(cursor.moveToNext());
		}
		return citys;
	}
	
	public List<County> loadCounty(int cid){
		List<County> countys=new ArrayList<County>();
		Cursor cursor=db.query("county", null, "cid = ?", new String[]{String.valueOf(cid)}, null, null, null);
		if(cursor.moveToFirst()){
			do{
				County county=new County();
				county.setCid(cursor.getInt(cursor.getColumnIndex("cid")));
				county.setCode(cursor.getString(cursor.getColumnIndex("code")));
				county.setName(cursor.getString(cursor.getColumnIndex("name")));
				county.setId(cursor.getInt(cursor.getColumnIndex("id")));
				countys.add(county);
			}while(cursor.moveToNext());
		}
		return countys;
	}
}
