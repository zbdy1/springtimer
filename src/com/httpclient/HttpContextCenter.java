package com.httpclient;

import org.apache.http.client.CookieStore;
import org.apache.http.client.protocol.ClientContext;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;

public class HttpContextCenter {
	private static HttpContext context =null;// new BasicHttpContext();
    public static HttpContext getContext(){
    	if(context==null){
    		context=new BasicHttpContext();
    		CookieStore cookieStore = new BasicCookieStore();
  		    context.setAttribute(ClientContext.COOKIE_STORE, cookieStore);
    	}
    	return context;
    }
    public static void clearContext(){
    	context=null;
    }
}
