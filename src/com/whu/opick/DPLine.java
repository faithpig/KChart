package com.whu.opick;


public class DPLine {
	double[][] nums;
	double[] a;
	double[] DPnums;
	DPLine(double[] a)
	{
		this.a = a;
		nums = new double[a.length][2];
		for(int i = 0; i<a.length; i++)
		{
			nums[i][0] = a[i];
			nums[i][1] = 0;
		}
	}
	
	public double calMax(double[] a)
	{
		double Max = a[0];
		for(int i = 1; i<a.length; i++)
		{
			if(a[i]>Max)
				Max = a[i];
		}
		return Max;
	}
	public double calMin(double[] a)
	{
		double Min = a[0];
		for(int i = 1; i<a.length; i++)
		{
			if(a[i]<Min)
				Min = a[i];
		}
		return Min;
	}
	
	public double calAverageValue(double[] a)
	{
		double temp = 0;
		for(int i = 0;i<a.length; i++)
			temp += a[i];
		return temp;
	}
	
	public double calTolerance(double Max, double Min)
	{
		return (Max-Min)/20;
	}
	
	public double calDistance(int leftPoint,int rightPoint, int nowPoint)
	{
		//POINT p1,p2,p;
		double a,b,c;
		double x1,y1,x2,y2,x,y;
		double dis=0;
		x1 = leftPoint;
		y1 = nums[leftPoint][0];
		x2 = rightPoint;
		y2 = nums[rightPoint][0];
		x =  nowPoint;
		y = nums[nowPoint][0];
		//斜率不存在的情况
		if (x1==x2)
		{
			dis=Math.abs(x-x1);
			return dis;
		}
		
		a=(y2-y1)/(x2-x1);
		b=-1;
		c=y1-(y2-y1)/(x2-x1)*x1;
		dis=Math.abs(a*x+b*y+c)/(Math.sqrt(a*a+b*b));
		return dis;
	}
	
	public void DouglasPeuker(int leftpoint,int rightpoint,double tolerance)
	{
		int i,maxindex;
		double dis,maxdis;
		maxdis=0;
		maxindex=0;
		for (i=leftpoint;i<rightpoint;i++)
		{
			dis=calDistance(leftpoint,rightpoint,i);
			if (dis>maxdis)
			{
				maxdis=dis;
				maxindex=i;
			}
		}

		if (maxdis>tolerance)
		{
			//若距离大于阈值，则将nums【maxindex】【1】设为1
			nums[maxindex][1]=1;
			DouglasPeuker(leftpoint,maxindex,tolerance);
			DouglasPeuker(maxindex,rightpoint,tolerance);//回调
		}
	}
	//在数组DPnums里面存储D-P后折线的每个点的（高度-平均值）/（Max-Min）
	public void DPnums(double[][] nums)
	{
		int temp1 = 0,temp2 = 0;
		for(int i = 0; i<nums.length; i++)
		{
			if((int)nums[i][1] == 1)
				++temp1;
		}
		DPnums = new double[temp1];
		for(int i = 0; i<nums.length; i++)
		{
			if((int)nums[i][1] == 1)
			{
				DPnums[temp2] = (nums[i][0]-calAverageValue(a))/(calMax(a)-calMin(a));
				++temp2;
			}
		}
	}
	
	public void DPnums(double[] nums)
	{
		DPnums = new double[nums.length];
		for(int i = 0; i<nums.length; i++)
		{
			DPnums[i] = (nums[i]-calAverageValue(a))/(calMax(a)-calMin(a));
			//DPnums[i] = nums[i];
		}
	}
}
