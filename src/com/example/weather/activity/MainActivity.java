package com.example.weather.activity;

import java.util.ArrayList;
import java.util.List;

import com.example.weather.R;
import com.example.weather.db.*;
import com.example.weather.tool.HttpBackInterface;
import com.example.weather.tool.HttpTool;
import com.example.weather.tool.Utility;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.*;
import android.widget.AdapterView.OnItemClickListener;

public class MainActivity extends Activity implements OnItemClickListener{

	public static final int ISPROVINCE=1;
	public static final int ISCITY=2;
	public static final int ISCOUNTY=3;
	
	protected int curlevel=0;
	
	protected WeatherDb db=null;
	
	protected List<Province> provinceList=null;
	protected List<City> cityList=null;
	protected List<County> countyList=null;

	protected Province selectProvince=null;
	protected City selectCity=null;
	protected County selectCounty=null;
	
	protected ArrayAdapter<String> adapter=null;
	protected List<String> dataList=null;
	
	protected ListView listView1=null;
	protected TextView titleText=null;
	protected ProgressDialog progress=null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO 自动生成的方法存根
		super.onCreate(savedInstanceState);
		
		SharedPreferences pre= PreferenceManager.getDefaultSharedPreferences(this);
		if(pre.getBoolean("selected", false)){
			Intent intent =new Intent(this,WeatherInfo.class);
			startActivity(intent);
			finish();
		}
		
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.mian_activity);
		
		listView1=(ListView)findViewById(R.id.list_view1);
		titleText=(TextView)findViewById(R.id.title);
		dataList=new ArrayList<String>();
		db=new WeatherDb(this);
		adapter=new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, dataList);
		listView1.setAdapter(adapter);
		listView1.setOnItemClickListener(this);
		queryProvince();
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View view, int index, long arg3) {
		// TODO 自动生成的方法存根
		
		switch(curlevel){
			case ISPROVINCE:
				selectProvince=provinceList.get(index);
				queryCity();
				break;
			case ISCITY:
				selectCity=cityList.get(index);
				queryCounty();
				break;
			case ISCOUNTY:
				String code=countyList.get(index).getCode();
				Intent intent=new Intent(this,WeatherInfo.class);
				intent.putExtra("code", code);
				startActivity(intent);
				finish();
				break;
		}
			
		
	}
	
	protected void queryProvince(){
		provinceList=db.loadProvince();

		if(provinceList.size()>0){

			dataList.clear();
			for(Province province : provinceList){
				dataList.add(province.getName());
			}

			adapter.notifyDataSetChanged();
			listView1.setSelection(0);
			curlevel=ISPROVINCE;
			titleText.setText("中国");
			Toast.makeText(this, "from local", Toast.LENGTH_SHORT).show();

		}else{
			Toast.makeText(this, "from server", Toast.LENGTH_SHORT).show();
			queryFromServer(null, "province");
		}
	}
	
	protected void queryCity(){
		cityList=db.loadCity(selectProvince.getId());
		if(cityList.size()>0){
			dataList.clear();
			for(City city:cityList)
				dataList.add(city.getName());
			adapter.notifyDataSetChanged();
			listView1.setSelection(0);
			curlevel=ISCITY;
			titleText.setText(selectProvince.getName());
		}else{
			queryFromServer(selectProvince.getCode(), "city");
		}
	}
	
	protected void queryCounty(){
		countyList=db.loadCounty(selectCity.getId());
		if(countyList.size()>0){
			dataList.clear();
			for(County county : countyList)
				dataList.add(county.getName());
			adapter.notifyDataSetChanged();
			listView1.setSelection(0);
			curlevel=ISCOUNTY;
			titleText.setText(selectCity.getName());
		}else{
			queryFromServer(selectCity.getCode(), "county");
		}
	}
	protected void queryFromServer(final String code,final String type){
		String host="";
		if(TextUtils.isEmpty(code)){
			host="http://www.weather.com.cn/data/list3/city.xml";
		}else{
			host="http://www.weather.com.cn/data/list3/city"+code+".xml";
		}
		showDialog();
		HttpTool.sendRequest(host, new HttpBackInterface() {
			
			@Override
			public void httpFinish(String re) {
				// TODO 自动生成的方法存根
				Boolean result=false;
				if("province".equals(type))
					result=Utility.handleProvince(db,re);
				else if("city".equals(type))
					result=Utility.handleCity(db, re, selectProvince.getId());
				else if("county".equals(type))
					result=Utility.handleCounty(db, re, selectCity.getId());
				
				if(!result)
					return;
				
				//回到主线程中
				runOnUiThread(new Runnable() {
					
					@Override
					public void run() {
						// TODO 自动生成的方法存根
						closeDialog();
						if("province".equals(type))
							queryProvince();
						else if("city".equals(type))
							queryCity();
						else if("county".equals(type))
							queryCounty();
					}
				});
			}
			
			@Override
			public void httpError() {
				// TODO 自动生成的方法存根
				runOnUiThread(new Runnable() {
					
					@Override
					public void run() {
						// TODO 自动生成的方法存根
						closeDialog();
						Toast.makeText(MainActivity.this, "加载失败", Toast.LENGTH_SHORT).show();
					}
				});
			}
		});
	}
	protected void showDialog(){
		if(progress==null){
			progress=new ProgressDialog(this);
			progress.setTitle("加载中");
			progress.setCanceledOnTouchOutside(false);
		}
		progress.show();
	}
	protected void closeDialog(){
		if(progress!=null)
			progress.dismiss();
	}
	
	protected void makeTextTo(String text){
		Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
	}

	@Override
	public void onBackPressed() {
		// TODO 自动生成的方法存根
		switch (curlevel) {
			case ISPROVINCE:
				finish();
				break;
			case ISCITY:
				queryProvince();
				break;
			case ISCOUNTY:
				queryCity();
				break;
		}
	}
	
	
}

