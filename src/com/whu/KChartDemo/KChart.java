package com.whu.KChartDemo;
 
import java.awt.Color;
import java.awt.Paint;
import java.io.File;
import java.io.IOException;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.jfree.chart.ChartFrame;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.axis.DateTickMarkPosition;
import org.jfree.chart.axis.DateTickUnit;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.NumberTickUnit;
import org.jfree.chart.axis.SegmentedTimeline;

import org.jfree.chart.plot.CombinedDomainXYPlot;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.CandlestickRenderer;
import org.jfree.chart.renderer.xy.XYBarRenderer;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;

import org.jfree.data.time.Day;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.data.time.ohlc.OHLCSeries;
import org.jfree.data.time.ohlc.OHLCSeriesCollection;



public class KChart{

	List<KData> klist = new ArrayList<KData>();//定义一个用来保存数据的集合类List
	String beginDate;//开始时间，格式是yyyy-MM-dd
	String endDate;//结束时间
 
	public KChart(List<KData> list,String beginDate,String endDate) {
		this.klist = list;
		this.beginDate = beginDate;
		this.endDate = endDate;
	}

	public void clearList() {// 清空保存数据的集合类List
		klist.clear();
	}

	public void show() {//画图显示
		JFreeChart chart = new JFreeChart(null, JFreeChart.DEFAULT_TITLE_FONT, CombinePlot(), false);
		chart.setBackgroundPaint(Color.white);
        ChartFrame frame = new ChartFrame("图", chart); 
        frame.setBackground(Color.white);
        frame.pack(); 
        frame.setVisible(true); 
	}
	
	public OHLCSeries getOHLS(){//获取k线数据序列
		OHLCSeries series = new OHLCSeries("");// 高开低收数据序列，股票K线图的四个数据，依次是开，高，低，收
		for(KData m: klist){
			String date[] = (new SimpleDateFormat("yyyy-MM-dd").format(m.getIssuse_date())).split("-");
			int year = Integer.parseInt(date[0]);
			int month = Integer.parseInt(date[1]);
			int day = Integer.parseInt(date[2]);
			series.add(new Day(day, month, year), m.getOpen_value(), m.getHigh_value(), m.getLow_value(), m.getClose_value());
		}
		return series;
	}
	
	public TimeSeries getTime(){//获取柱状图成交量序列
		TimeSeries series = new TimeSeries("");// 对应时间成交量数据
		for(KData m: klist){
			String date[] = (new SimpleDateFormat("yyyy-MM-dd").format(m.getIssuse_date())).split("-");
			int year = Integer.parseInt(date[0]);
			int month = Integer.parseInt(date[1]);
			int day = Integer.parseInt(date[2]);
			series.add(new Day(day, month, year), m.getVolume_value());
		}
		return series;
	}

    public CombinedDomainXYPlot CombinePlot() { 
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");// 设置日期格式
		double highValue = Double.MIN_VALUE;// 设置K线数据当中的最大值
		double minValue = Double.MAX_VALUE;// 设置K线数据当中的最小值
		double high2Value = Double.MIN_VALUE;// 设置成交量的最大值
		double min2Value = Double.MAX_VALUE;// 设置成交量的最低值

		final OHLCSeriesCollection seriesCollection = new OHLCSeriesCollection();// 保留K线数据的数据集，必须申明为final，后面要在匿名内部类里面用到
		seriesCollection.addSeries(getOHLS());

		TimeSeriesCollection timeSeriesCollection = new TimeSeriesCollection();// 保留成交量数据的集合
		timeSeriesCollection.addSeries(getTime());

		// 获取K线数据的最高值和最低值
		int seriesCount = seriesCollection.getSeriesCount();// 一共有多少个序列，目前为一个
		for (int i = 0; i < seriesCount; i++) {
			int itemCount = seriesCollection.getItemCount(i);// 每一个序列有多少个数据项
			for (int j = 0; j < itemCount; j++) {
				if (highValue < seriesCollection.getHighValue(i, j)) {// 取第i个序列中的第j个数据项的最大值
					highValue = seriesCollection.getHighValue(i, j);
				}
				if (minValue > seriesCollection.getLowValue(i, j)) {// 取第i个序列中的第j个数据项的最小值
					minValue = seriesCollection.getLowValue(i, j);
				}
			}
		}
		// 获取最高值和最低值
		int seriesCount2 = timeSeriesCollection.getSeriesCount();// 一共有多少个序列，目前为一个
		for (int i = 0; i < seriesCount2; i++) {
			int itemCount = timeSeriesCollection.getItemCount(i);// 每一个序列有多少个数据项
			for (int j = 0; j < itemCount; j++) {
				if (high2Value < timeSeriesCollection.getYValue(i, j)) {// 取第i个序列中的第j个数据项的值
					high2Value = timeSeriesCollection.getYValue(i, j);
				}
				if (min2Value > timeSeriesCollection.getYValue(i, j)) {// 取第i个序列中的第j个数据项的值
					min2Value = timeSeriesCollection.getYValue(i, j);
				}
			}
		}

		final CandlestickRenderer candlestickRender = new CandlestickRenderer();// 设置K线图的画图器，必须申明为final，后面要在匿名内部类里面用到
		candlestickRender.setUseOutlinePaint(true); // 设置是否使用自定义的边框线，程序自带的边框线的颜色不符合中国股票市场的习惯
		candlestickRender.setAutoWidthMethod(CandlestickRenderer.WIDTHMETHOD_AVERAGE);// 设置如何对K线图的宽度进行设定
		candlestickRender.setAutoWidthGap(0.001);// 设置各个K线图之间的间隔
		candlestickRender.setUpPaint(Color.RED);// 设置股票上涨的K线图颜色
		candlestickRender.setDownPaint(Color.GREEN);// 设置股票下跌的K线图颜色
		candlestickRender.setCandleWidth(4D);
		candlestickRender.setBaseOutlinePaint(new Color(200, 200, 200));
		candlestickRender.setSeriesCreateEntities(1, true);

		DateAxis x1Axis = new DateAxis();// 设置x轴，也就是时间轴
		x1Axis.setAutoRange(false);// 设置不采用自动设置时间范围
		try {
			x1Axis.setRange(dateFormat.parse(beginDate),
					dateFormat.parse(endDate));// 设置时间范围，注意时间的最大值要比已有的时间最大值要多一天
		} catch (Exception e) {
			e.printStackTrace();
		}
		x1Axis.setTimeline(SegmentedTimeline.newMondayThroughFridayTimeline());// 设置时间线显示的规则，用这个方法就摒除掉了周六和周日这些没有交易的日期(很多人都不知道有此方法)，使图形看上去连续
		x1Axis.setAutoTickUnitSelection(true);// 设置不采用自动选择刻度值
		x1Axis.setTickMarkPosition(DateTickMarkPosition.MIDDLE);// 设置标记的位置
		x1Axis.setStandardTickUnits(DateAxis.createStandardDateTickUnits());// 设置标准的时间刻度单位
		x1Axis.setTickUnit(new DateTickUnit(DateTickUnit.DAY, 7));// 设置时间刻度的间隔，一般以周为单位
		x1Axis.setDateFormatOverride(new SimpleDateFormat("yyyy-MM-dd"));// 设置显示时间的格式
		x1Axis.setVisible(false);
		NumberAxis y1Axis = new NumberAxis();// 设定y轴，就是数字轴
		y1Axis.setAutoRange(false);// 不不使用自动设定范围
		y1Axis.setRange(minValue * 0.9, highValue * 1.1);// 设定y轴值的范围，比最低值要低一些，比最大值要大一些，这样图形看起来会美观些
		y1Axis.setTickUnit(new NumberTickUnit(
				(highValue * 1.1 - minValue * 0.9) / 10));// 设置刻度显示的密度
		XYPlot plot1 = new XYPlot(seriesCollection, x1Axis, y1Axis,
				candlestickRender);// 设置画图区域对象
		plot1.setDomainGridlinesVisible(false);
		plot1.setRangeGridlinesVisible(false);
		plot1.setBackgroundPaint(Color.white);
		
		XYLineAndShapeRenderer line1 = new XYLineAndShapeRenderer(true,false);//5日均价
		line1.setSeriesPaint(1, Color.red);
		plot1.setDataset(1, timeSeriesCollection);
		plot1.setRenderer(1, line1);
		

		XYBarRenderer xyBarRender = new XYBarRenderer() {
			private static final long serialVersionUID = 1L;// 为了避免出现警告消息，特设定此值

			public Paint getItemPaint(int i, int j) {// 匿名内部类用来处理当日的成交量柱形图的颜色与K线图的颜色保持一致
				if (seriesCollection.getCloseValue(i, j) > seriesCollection.getOpenValue(i, j)) {// 收盘价高于开盘价，股票上涨，选用股票上涨的颜色
					return candlestickRender.getUpPaint();
				} else {
					return candlestickRender.getDownPaint();
				}
			}
		};

		xyBarRender.setMargin(0.1);// 设置柱形图之间的间隔
		NumberAxis y2Axis = new NumberAxis();// 设置Y轴，为数值,后面的设置，参考上面的y轴设置
		y2Axis.setAutoRange(false);
		y2Axis.setRange(min2Value * 0.9, high2Value * 1.1);
		y2Axis.setTickUnit(new NumberTickUnit(
				(high2Value * 1.1 - min2Value * 0.9) / 4));
		y2Axis.setVisible(false);
		y1Axis.setVisible(false);
		XYPlot plot2 = new XYPlot(timeSeriesCollection, null, y2Axis,xyBarRender);// 建立第二个画图区域对象，主要此时的x轴设为了null值，因为要与第一个画图区域对象共享x轴
		plot2.setDomainGridlinesVisible(false);
		plot2.setRangeGridlinesVisible(false);
		plot2.setBackgroundPaint(Color.white);
		plot1.setOutlineVisible(true);
		plot2.setOutlineVisible(true);
		

				
		CombinedDomainXYPlot combineddomainxyplot = new CombinedDomainXYPlot(x1Axis);// 建立一个恰当的联合图形区域对象，以x轴为共享轴
		combineddomainxyplot.add(plot1, 2);// 添加图形区域对象，后面的数字是计算这个区域对象应该占据多大的区域2/3
		combineddomainxyplot.add(plot2, 1);// 添加图形区域对象，后面的数字是计算这个区域对象应该占据多大的区域1/3
		combineddomainxyplot.setGap(10);// 设置两个图形区域对象之间的间隔空间
		combineddomainxyplot.setDomainGridlinesVisible(false);
		return combineddomainxyplot;
	}
    
    public boolean savaAsPic(String path){
    	JFreeChart chart = new JFreeChart(null, JFreeChart.DEFAULT_TITLE_FONT, CombinePlot(), false);
    	try {
			ChartUtilities.saveChartAsJPEG(new File(path), chart, 600, 480);
			System.out.println(path);
			return true;
		} catch (IOException e) {
			return false;
		}
    }

}