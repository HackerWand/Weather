package com.example.weather.tool;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;

import org.apache.http.HttpConnection;

public class HttpTool {

	public static void sendRequest(final String host,final HttpBackInterface back){
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				// TODO �Զ����ɵķ������
				HttpURLConnection connection=null;
				try {
					//����url����
					URL url = new URL(host);
					//��url�����л��httpUrl����
					connection=(HttpURLConnection)url.openConnection();
					//�������󷽷�
					connection.setRequestMethod("GET");
					//�������ӳ�ʱʱ��
					connection.setConnectTimeout(8000);
					//���ö�ȡ��ʱʱ��
					connection.setReadTimeout(8000);
					//�������л��������
					InputStream in=connection.getInputStream();
					//ͨ�������������������ȡ���ٻ�û����ȡ��
					BufferedReader reader=new BufferedReader(new InputStreamReader(in));
					StringBuilder builder=new StringBuilder();
					String line="";
					while((line=reader.readLine())!=null){
						builder.append(line);
					}
					
					//����лص�����ûص�
					if(back!=null){
						back.httpFinish(builder.toString());
					}
					
				} catch (Exception e) {
					// TODO �Զ����ɵ� catch ��
					e.printStackTrace();
					back.httpError();
				}finally{
					if(connection!=null)
						connection.disconnect();
				}
			}
		}).start();
	}
	
	
	
}
