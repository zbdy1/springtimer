package com.springtimer;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Map;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.dbconfig.DBConnTools;
import com.dbconfig.DBProperties;
import com.pojo.TouTiaoBean;
import com.util.Common;
@Component  //import org.springframework.stereotype.Component;  
public class ZengJiaCiShuTimer implements SpringTimerInterface{
	
	@Scheduled(cron="0 0 7-23 * * ? ")   //每小时执行一次
	@Override
	public void run() {
		Map<String,Object> dbProperties=DBProperties.dbIntconfig;
		DBConnTools.connMysqlDB(dbProperties);
		Calendar calendar=Calendar.getInstance();
		calendar.add(Calendar.DAY_OF_YEAR, -2);
		String dateStart=Common.getDateString(calendar.getTime(), "yyyy-MM-dd");
		String updateSql="update my_information set hit=hit+addfit(5,10) where FROM_UNIXTIME(begintime, '%Y-%m-%d' )>='"+dateStart+"'";
		System.out.println(updateSql);
		try {
			DBConnTools.myConn.createStatement().executeUpdate(updateSql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
