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

@Component
// import org.springframework.stereotype.Component;
public class ZengJiaCiShuTimer implements SpringTimerInterface {

	@Scheduled(cron = "0 0 7-23 * * ? ")
	// 每小时执行一次
	@Override
	public void run() {
		Map<String, Object> dbProperties = DBProperties.dbIntconfig;
		DBConnTools.connMysqlDB(dbProperties);
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DAY_OF_YEAR, -2);
		String dateStart = Common.getDateString(calendar.getTime(),
				"yyyy-MM-dd");
		String updateSql = "update my_information set hit=hit+addfit(5,10) where FROM_UNIXTIME(begintime, '%Y-%m-%d' )>='"
				+ dateStart + "'";
		System.out.println(updateSql);
		try {
			DBConnTools.myConn.createStatement().executeUpdate(updateSql);
			String focusIds=getFocusIds();
			if(focusIds!=null){
				String updateSql2="update my_information set hit=hit+addfit(5,10) where id in ("+focusIds+")";
				System.out.println(updateSql2);
				DBConnTools.myConn.createStatement().executeUpdate(updateSql2);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public String getFocusIds() {
		String query = "select url from my_focus";
		try {
			ResultSet rs = DBConnTools.myConn.createStatement().executeQuery(
					query);
			StringBuffer resultIds = new StringBuffer();
			while (rs.next()) {
				String url = rs.getString("url");
				if(url.startsWith("http://www.dlxc6.com/m/index.php?mod=information")){
					String id = url.substring(url.lastIndexOf("=") + 1);
					resultIds.append(id + ",");
				}
			}
			if (resultIds.length() > 0) {
				return resultIds.substring(0, resultIds.length() - 1);
			} else {
				return null;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}
