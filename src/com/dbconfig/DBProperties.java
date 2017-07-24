package com.dbconfig;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

public class DBProperties {
	
	public static Map<String, Object> dbIntconfig = new HashMap<String, Object>();
	
	static{
		getDBProperties();
	}
	public static void getDBProperties() {
		
		ResourceBundle rb = ResourceBundle.getBundle("config");
		Enumeration<String> cfgs = rb.getKeys();
		while (cfgs.hasMoreElements()) {
			String key = cfgs.nextElement();
			String val = rb.getString(key);
			dbIntconfig.put(key, val);
		}
	}
	
	
}
