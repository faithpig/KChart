package com.whu.KChartDemo;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class KData {
	
private String stock_name;//股票名称

private Date issuse_date;//日期

private double open_value;//开盘价

private double high_value;//最高价

private double low_value;//最低价

private double close_value;//收盘价

private double volume_value;//成交量

private double turnover;//成交额

public KData(){
}

public KData(String stock_name,String issuse_date,double open_value,double high_value,double low_value,double close_value,double volume_value,double turnover){
	this.stock_name = stock_name;
	SimpleDateFormat sf =new SimpleDateFormat("MM/dd/yyyy");
	try {
		System.out.println(issuse_date);
		this.issuse_date = sf.parse(issuse_date);
	} catch (ParseException e) {
		System.out.println("日期格式弄错了！");
	}
	this.open_value = open_value;
	this.high_value = high_value;
	this.low_value = low_value;
	this.close_value = close_value;
	this.volume_value = volume_value;
	this.turnover = turnover;
}

public double getTurnover() {
	return turnover;
}

public void setTurnover(double turnover) {
	this.turnover = turnover;
}
public String getStock_name() {
	return stock_name;
}
public void setStock_name(String stock_name) {
	this.stock_name = stock_name;
}
public Date getIssuse_date() {
	return issuse_date;
}
public void setIssuse_date(Date issuse_date) {
	this.issuse_date = issuse_date;
}
public double getOpen_value() {
	return open_value;
}
public void setOpen_value(double open_value) {
	this.open_value = open_value;
}
public double getHigh_value() {
	return high_value;
}
public void setHigh_value(double high_value) {
	this.high_value = high_value;
}
public double getLow_value() {
	return low_value;
}
public void setLow_value(double low_value) {
	this.low_value = low_value;
}
public double getClose_value() {
	return close_value;
}
public void setClose_value(double close_value) {
	this.close_value = close_value;
}
public double getVolume_value() {
	return volume_value;
}
public void setVolume_value(double volume_value) {
	this.volume_value = volume_value;
}

}
