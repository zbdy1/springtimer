package com.pojo;

import java.util.List;

public class TouTiaoResult {
	private String stat;
	private List<TouTiaoBean> data;
	public String getStat() {
		return stat;
	}
	public void setStat(String stat) {
		this.stat = stat;
	}
	public List<TouTiaoBean> getData() {
		return data;
	}
	public void setData(List<TouTiaoBean> data) {
		this.data = data;
	}
	
}
