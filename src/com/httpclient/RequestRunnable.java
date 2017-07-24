package com.httpclient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.HTTP;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;

import com.util.Common;


public abstract class RequestRunnable implements Runnable{
	private String path = "";
	private String chartSet="UTF-8";
	List<NameValuePair> params=null;
	boolean postFlag;
	private HttpContext httpContext=null;
	boolean imageFlag=false;
	private HashMap<String,String> header;
	public RequestRunnable(String path,List<NameValuePair> params,boolean postFlag,boolean isImage){
		this.path=path;
		this.params=params;
		this.postFlag=postFlag;
		this.imageFlag=isImage;
	}
	public RequestRunnable(String path,List<NameValuePair> params,boolean postFlag){
		this.path=path;
		this.params=params;
		this.postFlag=postFlag;
	}
	public void setHttpContext(HttpContext httpContext){
		this.httpContext=httpContext;
	}
	public HttpContext getHttpContext(){
		return httpContext;
	}
	public abstract void afterRequest(String result);
	public void run() {
			HttpClient client = new DefaultHttpClient();
			HttpUriRequest post=null;
			if(postFlag){
				post = new HttpPost(path);		
				try {
					((HttpPost)post).setEntity(new UrlEncodedFormEntity(params,HTTP.UTF_8));
				} catch (UnsupportedEncodingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}else{
				if(params.size()>0){
					path+="?"+params.get(0).getName()+"="+params.get(0).getValue();
					for(int i=1;i<params.size();i++){
						path+="&"+params.get(i).getName()+"="+params.get(i).getValue();
					}
				}
				 post=new HttpGet(path);
			}		
			if(header!=null){
				for(String key:header.keySet()){
					post.addHeader(key, header.get(key));
				}
			}
			try {
				if(httpContext==null){
					httpContext=HttpContextCenter.getContext();
					
				}
				HttpResponse response = client.execute(post,httpContext);
				HttpEntity resEntity = response.getEntity();
		          if (resEntity != null) {
		        	  afterRequest(EntityUtils.toString(resEntity,Charset.forName(chartSet)));
			          resEntity.consumeContent();
		          }
			} catch (IOException e) {
				e.printStackTrace();
			} 
	
		}
	public HashMap<String, String> getHeader() {
		return header;
	}
	public void setHeader(HashMap<String, String> header) {
		this.header = header;
	}

	public  String unicode2Str(String str) {

		StringBuffer sb = new StringBuffer();
		String[] arr = str.split("\\\\u");
		int len = arr.length;
		sb.append(arr[0]);
		for (int i = 1; i < len; i++) {
			String tmp = arr[i];
			char c = (char) Integer.parseInt(tmp.substring(0, 4), 16);
			sb.append(c);
			sb.append(tmp.substring(4));
		}
		return sb.toString();
	}
}
