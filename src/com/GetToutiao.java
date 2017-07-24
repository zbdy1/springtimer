package com;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.apache.http.NameValuePair;
import com.dbconfig.DBConnTools;
import com.dbconfig.DBProperties;
import com.google.gson.Gson;
import com.pojo.TouTiao;
import com.pojo.TouTiaoBean;
import com.pojo.TouTiaoResult;

public class GetToutiao extends com.httpclient.RequestRunnable{
	private String insertSql="INSERT INTO `my_news_toutiao` (title,date,author_name,thumbnail_pic_s,thumbnail_pic_s02,thumbnail_pic_s03,url,realtype,type,uniquekey)VALUES(?,?,?,?,?,?,?,?,?,?)";
	public GetToutiao(String path, List<NameValuePair> params, boolean postFlag) {
		super(path, params, postFlag);
	}
	@Override
	public void afterRequest(String result) {
		Map<String,Object> dbProperties=DBProperties.dbIntconfig;
		Gson gson=new Gson();
		TouTiao toutiao=gson.fromJson(result, TouTiao.class);
		TouTiaoResult toutiaoResult=toutiao.getResult();
		List<TouTiaoBean> list=toutiaoResult.getData();
		DBConnTools.connMysqlDB(dbProperties);
		List<TouTiaoBean> saveBatch=new ArrayList<TouTiaoBean>();
		for(TouTiaoBean bean:list){
			try {
				if(!existKey(DBConnTools.myConn,bean)){
					saveBatch.add(bean);
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		save(DBConnTools.myConn,insertSql,saveBatch);
	}
	public boolean existKey(Connection con,TouTiaoBean bean) throws SQLException{
		String uniquekey=bean.getUniquekey();
		String query="SELECT uniquekey FROM `my_news_toutiao` WHERE uniquekey ='"+uniquekey+"'";
		ResultSet rs=con.createStatement().executeQuery(query);
		if(rs.next()){
			//rs.close();
			return true;
		}else{
			//rs.close();
			return false;
		}
	}
	//保存数据
	public void save(Connection con,String sql,List<TouTiaoBean> list){
		try {
			PreparedStatement ps=con.prepareStatement(sql);
			for(TouTiaoBean bean:list){
				ps.setObject(1,bean.getTitle());
				ps.setObject(2,bean.getDate());
				ps.setObject(3,bean.getAuthor_name());
				ps.setObject(4,bean.getThumbnail_pic_s());
				ps.setObject(5,bean.getThumbnail_pic_s02());
				ps.setObject(6,bean.getThumbnail_pic_s03());
				ps.setObject(7,bean.getUrl());
				String category=bean.getCategory();
				if(category==null||"".equals(category)){
					category="时尚";
				}
				ps.setObject(8,category);
				ps.setObject(9,category);
				ps.setObject(10,bean.getUniquekey());
				ps.addBatch();
			}
			System.out.println("存库"+ps.executeBatch().length+"条");
		} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
		}
	}
}
