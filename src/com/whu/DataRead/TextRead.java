package com.whu.DataRead;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;
import java.util.regex.Pattern;

public class TextRead {
	private String regEx = "[\u4e00-\u9fa5]";
	private Pattern pat = Pattern.compile(regEx);
	private File f;
	
	private String jpeg_path = null;//图片文件名;
	private String stock_name = null;//股票名称
	private String[] issuse_date = null;//日期
	private double[] open_value = null;//开盘价
	private double[] high_value = null;//最高价
	private double[] low_value = null;//最低价
	private double[] close_value = null;//收盘价
	private double[] volume_value = null;//成交量
	private double[] turnover_value = null;//成交额
	
	public void init(File f)
	{
		this.f = f;
		jpeg_path = f.getParent()+"/"+f.getName()+".jpg";
		readtext();
	}
	private void readtext()
	{
		try{
			stock_name = f.getName();
			InputStreamReader read = new InputStreamReader(
					new FileInputStream(f));
            BufferedReader bf = new BufferedReader(read);
            String s;
            Queue<String> q_data = new LinkedList<String>();
            Queue<String> q_open = new LinkedList<String>();
            Queue<String> q_high = new LinkedList<String>();
            Queue<String> q_low = new LinkedList<String>();
            Queue<String> q_close = new LinkedList<String>();
            Queue<String> q_volume = new LinkedList<String>();
            Queue<String> q_turnover = new LinkedList<String>();
            s = bf.readLine();
            s = bf.readLine();
            while( (s=bf.readLine()) != null)
            {
            	String data[] = s.split("\t");
            	
            	if (data.length == 7)
            	{
            		q_data.offer(data[0]);
            		q_open.offer(data[1]);
            		q_high.offer(data[2]);
            		q_low.offer(data[3]);
            		q_close.offer(data[4]);
            		q_volume.offer(data[5]);
            		q_turnover.offer(data[6]);
            	}
            }
            read.close();
            bf.close();
            System.out.println(q_data.size());
            issuse_date = new String[q_data.size()];
            open_value = new double[q_open.size()];
            high_value = new double[q_high.size()];
            low_value = new double[q_low.size()];
            close_value = new double[q_close.size()];
            volume_value = new double[q_volume.size()];
            turnover_value = new double[q_turnover.size()];
            String head;
            int tag = 0;
            while((head = q_data.poll())!= null)
            {
            	issuse_date[tag] = head;
            	open_value[tag] = Double.parseDouble(q_open.poll());
            	high_value[tag] = Double.parseDouble(q_high.poll());
            	low_value[tag] = Double.parseDouble(q_low.poll());
            	close_value[tag] = Double.parseDouble(q_close.poll());
            	volume_value[tag] = Double.parseDouble(q_volume.poll());
            	turnover_value[tag] = Double.parseDouble(q_turnover.poll());
            	++ tag;
            }
            System.out.println(issuse_date.length);
            System.out.println(f.getName());
		}catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public String get_stock_name() {return stock_name;}
	public String get_jpeg_path() {return jpeg_path;}
	public String[] get_issuse_date(){return issuse_date;}
	public double[] get_open_value() {return open_value;}
	public double[] get_high_value() {return high_value;}
	public double[] get_low_value() {return low_value;}
	public double[] get_close_value() {return close_value;}
	public double[] get_volume_value() {return volume_value;}
	public double[] get_turnover_value() {return turnover_value;}
}

