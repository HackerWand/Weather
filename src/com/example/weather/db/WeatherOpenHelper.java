package com.example.weather.db;

import java.util.List;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class WeatherOpenHelper extends SQLiteOpenHelper{

	//创建省份表
	private final static String CREATE_PROVINCE="create table province("
			+"id integer primary key autoincrement,"
			+"name text,"
			+"code text)";
	
	private final static String CREATE_CITY="create table city("
			+"id integer primary key autoincrement,"
			+"name text,"
			+"code text,"
			+"pid integer)";
	private final static String CREATE_COUNTY="create table county("
			+"id integer primary key autoincrement,"
			+"name text,"
			+"code text,"
			+"cid integer)";
	
	
	public WeatherOpenHelper(Context context, String name,
			CursorFactory factory, int version) {
		super(context, name, factory, version);
		// TODO 自动生成的构造函数存根
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO 自动生成的方法存根
		db.execSQL(CREATE_PROVINCE);
		db.execSQL(CREATE_CITY);
		db.execSQL(CREATE_COUNTY);
	}

	@Override
	public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {
		// TODO 自动生成的方法存根
		
	}
	

}
