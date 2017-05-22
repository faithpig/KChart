package com.whu.opick;
import java.io.File;


public class Test {
	private double[][] sums=new double[2][];
	DPLine line = null;
	
	public double compare(String path1 ,String path2)
	{
		File f1 = new File(path1);
		File f2 = new File(path2);
		//File[] f = (new File(f1.getParent())).listFiles();
		WangRead data = new WangRead();
		data.init(f1);
		sums[0]=data.get_low_value();
		data.init(f2);
		sums[1]=data.get_low_value();
		return semComparer(sums[0], sums[1]);
	}
	
	
	public double semComparer(double[] a, double[] b)
	{
		//存储相似度数据
		double temp=0;
		if(a.length<b.length)
		{
			for(int i = 0; i<a.length; i++)
			{
				b[i] = b[i]-(b[0]-a[0]);
			}
			for(int i = 0; i<a.length; i++)
			{

				temp += Math.pow(Math.abs((b[i]-a[i]))/a[i],2);
			}
			return Math.sqrt(temp)/2;
		}
		else
		{
			for(int i = 0; i<b.length; i++)
			{
				b[i] = b[i]-(b[0]-a[0]);
			}
			for(int i = 0; i<b.length; i++)
			{
					temp += Math.pow(Math.abs((b[i]-a[i]))/a[i],2);
			}
			return Math.sqrt(temp)/2;
		}
	}
	
	public void DescInsertionSort(double[][] temp)
	{
		double key1 = 0,key2 = 0;
		int i = 0,j = 0;
		for(j = 1; j<temp.length; j++)
		{
			key1 = temp[j][0];
			key2 = temp[j][1];
			i = j-1;
			while(i>=0 && temp[i][0]<key1)
			{
				temp[i+1][0] = temp[i][0];
				temp[i+1][1] = temp[i][1];
				i = i-1;
			}
			temp[i+1][0] =key1;
			temp[i+1][1] =key2;
		}
	}
}
