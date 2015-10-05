package com.example.weather.tool;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import android.util.Log;

public class HttpTool {

	public static void sendRequest(final String host,final HttpBackInterface back){
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				// TODO 自动生成的方法存根
				HttpURLConnection connection=null;
				try {
					//构建url对象
					URL url = new URL(host);
					//从url对象中获得httpUrl连接
					connection=(HttpURLConnection)url.openConnection();
					//设置请求方法
					connection.setRequestMethod("GET");
					//设置连接超时时间
					connection.setConnectTimeout(8000);
					//设置读取超时时间
					connection.setReadTimeout(8000);
					//从连接中获得输入流
					InputStream in=connection.getInputStream();
					//通过输入流获得输入流读取器再获得缓冲读取器
					BufferedReader reader=new BufferedReader(new InputStreamReader(in));
					StringBuilder builder=new StringBuilder();
					String line="";
					while((line=reader.readLine())!=null){
						builder.append(line);
					}
					
					//如果有回调则调用回调
					if(back!=null){
						back.httpFinish(builder.toString());
					}
					
				} catch (Exception e) {
					// TODO 自动生成的 catch 块
					e.printStackTrace();
					Log.d("xxxxxxxxxx",e.getMessage());
					back.httpError();
				}finally{
					if(connection!=null)
						connection.disconnect();
				}
			}
		}).start();
	}
	
	
	
}
