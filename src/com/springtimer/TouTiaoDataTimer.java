package com.springtimer;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.GetToutiao;
import com.dbconfig.DBProperties;
@Component  //import org.springframework.stereotype.Component;  
public class TouTiaoDataTimer implements SpringTimerInterface{
	public Date startDate;
	public Date endDate;
	@Scheduled(cron="0 0 0/1 * * ? ")   //每小时执行一次
     @Override  
	public void run(){
		 String param[]=new String[]{"shehui","guonei","guoji","yule","tiyu","junshi","keji","caijing","shishang"};
		 Map<String,Object> dbProperties=DBProperties.dbIntconfig;
		 String toutiaoUrl=dbProperties.get("toutiaoUrl").toString();
		 String toutiaoKey=dbProperties.get("toutiaoKey").toString();
		 for(int i=0;i<param.length;i++){
			 List<NameValuePair> list=new ArrayList<NameValuePair>();
			 boolean post=true;
			 list.add(new BasicNameValuePair("type",param[i]));
			 list.add(new BasicNameValuePair("key",toutiaoKey));
			 GetToutiao getToutiao=new GetToutiao(toutiaoUrl, list, post);
			 new Thread(getToutiao).start();
		 }
	}
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
}
