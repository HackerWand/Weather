package com.example.weather.activity;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.*;

import com.example.weather.R;
import com.example.weather.tool.HttpBackInterface;
import com.example.weather.tool.HttpTool;
import com.example.weather.tool.Utility;

public class WeatherInfo extends Activity implements OnClickListener{
	
	protected TextView cityName=null;
	protected TextView time=null;
	protected TextView nowTime=null;
	protected TextView weatherText=null;
	protected TextView temp1=null;
	protected TextView temp2=null;
	protected Button switchCity=null;
	protected Button updateInfo=null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO 自动生成的方法存根
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.weatherinfo);
		
		cityName=(TextView)findViewById(R.id.city_name);
		time=(TextView)findViewById(R.id.push_time);
		nowTime=(TextView)findViewById(R.id.cur_time);
		weatherText=(TextView)findViewById(R.id.weather_desp);
		temp1=(TextView)findViewById(R.id.temp1);
		temp2=(TextView)findViewById(R.id.temp2);
		switchCity=(Button)findViewById(R.id.switch_city);
		updateInfo=(Button)findViewById(R.id.update);
		
		switchCity.setOnClickListener(this);
		updateInfo.setOnClickListener(this);
		
		//判断是否已经选中的地区
		SharedPreferences preferences=PreferenceManager.getDefaultSharedPreferences(this);
		if(!preferences.getBoolean("selected", false)){
			Intent intent = getIntent();
			String countyCode=intent.getStringExtra("code");
			queryWeatherCode(countyCode);
			Toast.makeText(this, "is selected is true", Toast.LENGTH_SHORT).show();
		}else{
			showWeatherInfo();
			Toast.makeText(this, "is selected is false", 1).show();
		}
	}
	
	protected void showWeatherInfo(){
		SharedPreferences preferences= PreferenceManager.getDefaultSharedPreferences(this);
		cityName.setText(preferences.getString("name", ""));
		time.setText("天气更新时间："+preferences.getString("pushTime", ""));
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy年M月d日",Locale.CHINA);
		nowTime.setText(sdf.format(new Date()));
		weatherText.setText(preferences.getString("desp", ""));
		temp1.setText(preferences.getString("temp1", ""));
		temp2.setText(preferences.getString("temp2", ""));
	}
	
	protected void queryWeatherCode(String countyCode){
		String host="http://www.weather.com.cn/data/list3/city"+countyCode+".xml";
		queryFromServer(host, "weathercode");
	}
	
	protected void queryWeatherInfo(String weatherCode){
		String host="http://www.weather.com.cn/adat/cityinfo/"+weatherCode+".html";
		queryFromServer(host, "weatherinfo");
	}
	
	protected void queryFromServer(final String host,final String type){
		HttpTool.sendRequest(host, new HttpBackInterface() {
			
			@Override
			public void httpFinish(String re) {
				// TODO 自动生成的方法存根
				if("weathercode"==type){
					String tempArray[]=re.split("\\|");
					if(tempArray!=null&&tempArray.length==2){
						queryWeatherInfo(tempArray[1]);
					}
				}else if("weatherinfo"==type){
					Utility.handleJSON(WeatherInfo.this, re);
					
					runOnUiThread(new Runnable() {
						
						@Override
						public void run() {
							// TODO 自动生成的方法存根
							showWeatherInfo();
						}
					});
				}
				
			}
			
			@Override
			public void httpError() {
				// TODO 自动生成的方法存根
				runOnUiThread(new Runnable() {
					
					@Override
					public void run() {
						// TODO 自动生成的方法存根
						Toast.makeText(WeatherInfo.this, "加载天气信息失败", Toast.LENGTH_SHORT).show();
					}
				});
			}
		});
	}

	@Override
	public void onClick(View view) {
		// TODO 自动生成的方法存根
		switch(view.getId()){
			case R.id.switch_city:
				Intent intent=new Intent(this,MainActivity.class);
				intent.putExtra("switch", true);
				startActivity(intent);
				SharedPreferences.Editor editor=PreferenceManager.getDefaultSharedPreferences(this).edit();
				editor.putBoolean("selected", false);
				editor.commit();
				finish();
				break;
			case R.id.update:
				time.setText("更新中。。");
				SharedPreferences pre=PreferenceManager.getDefaultSharedPreferences(this);
				if(!TextUtils.isEmpty(pre.getString("id", "")))
					queryWeatherInfo(pre.getString("id", ""));
				break;
		}
	}
}
