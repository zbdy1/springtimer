package com;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import com.dbconfig.DBProperties;

public class Test {
	public static void main(String[] args) {
		 System.out.println(1);
		 String param[]=new String[]{"shehui","guonei","guoji","yule","tiyu","junshi","keji","caijing","shishang"};
		 Map<String,Object> dbProperties=DBProperties.dbIntconfig;
		 String toutiaoUrl=dbProperties.get("toutiaoUrl").toString();
		 String toutiaoKey=dbProperties.get("toutiaoKey").toString();
		 List<NameValuePair> list=new ArrayList<NameValuePair>();
		 boolean post=true;
		 list.add(new BasicNameValuePair("type",param[0]));
		 list.add(new BasicNameValuePair("key",toutiaoKey));
		 GetToutiao getToutiao=new GetToutiao(toutiaoUrl, list, post);
		 new Thread(getToutiao).start();
	}
}
