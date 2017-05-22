package com.whu.action;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;
import com.whu.service.HomeService;
import com.whu.vo.AlgorithmWeight;
import com.whu.vo.Answer;
import com.whu.vo.ArrayAnswer;
import com.whu.vo.Rank;

public class HomeAction extends ActionSupport{

	private static final long serialVersionUID = -2571245437203015537L;

	private HomeService homeSer = new HomeService();
	
	private String inPic;//目标图片路径
	private String outPic;//最佳对比图片路径
	private AlgorithmWeight aw;//算法权重
	private String method = "";//用户在主界面点击哪个按钮,A、B、C、ABC分别代表算法1、2、3、混合算法
	private double rate;//最佳相似度
	private String[] outPicList;//所有对比图片路径
	private double[] rateList;//所有对比图片相似度
	private String[] outPicListRank;//所有对比图片路径按相似度大小排序
	private double[] rateListRank;//所有对比图片相似度按相似度大小排序
	private int size;//对比图片的总个数


	public String welcome(){
		ArrayAnswer arrAns = null;
		if("".equals(method.trim())){
			inPic = "";
		}
		else if("init".equals(method.trim())){
			//生成所有样本图片
			HttpServletRequest req = getServeletContext();
			Answer aws = homeSer.getTarget(req.getSession().getServletContext().getRealPath("/"));
			System.out.println(req.getSession().getServletContext().getRealPath("/"));
			inPic = aws.getPath();
			HttpServletRequest request = ServletActionContext.getRequest();
			request.setAttribute("win", "<script type='text/javascript'>window.alert('导入成功');</script>");
			request.setAttribute("flag","true");
			return "index";
		}
		else if("A".equals(method.trim())){
			if(!"".equals(inPic)){
				HttpServletRequest req = getServeletContext();
				arrAns = homeSer.getABest(req.getSession().getServletContext().getRealPath("/"));
			}
		}
		else if("B".equals(method.trim())){
			if(!"".equals(inPic)){
				HttpServletRequest req = getServeletContext();
				arrAns = homeSer.getBBest(req.getSession().getServletContext().getRealPath("/"));
			}
		}
		else if("C".equals(method.trim())){
			if(!"".equals(inPic)){
				HttpServletRequest req = getServeletContext();
				arrAns = homeSer.getCBest(req.getSession().getServletContext().getRealPath("/"));
			}
		}
		else if("ABC".equals(method.trim())){
			HttpServletRequest req = getServeletContext();
			if((aw.getWeight1()==0&&aw.getWeight2()==0&&aw.getWeight3()==0)||"".equals(inPic)){
			}
			else{
				arrAns = homeSer.getThreeBest(aw.getWeight1(),aw.getWeight2(),aw.getWeight3(),req.getSession().getServletContext().getRealPath("/"));
			}

		}
		else{
			inPic = "";
		}
		if(arrAns!=null){
			String[] allNames = homeSer.getAllNames(getServeletContext().getSession().getServletContext().getRealPath("/"));
			Rank[] rank = arrAns.getRank();
			outPicList = arrAns.getPath();
			rateList = arrAns.getRate();
			rate = rank[0].getRate();
			outPic = outPicList[rank[0].getTag()-1];
			size = arrAns.getSize();
			rateListRank = new double[size];
			outPicListRank = new String[size];
			for(int i=0;i<size;i++){
				rateListRank[i] = rank[i].getRate();
				outPicListRank[i] = "picList/"+allNames[rank[i].getTag()]+".jpg";
				System.out.println(rateListRank[i]);
			}
		}
		else{
			outPic = "";
			rate = 0;
			outPicList = null;
			rateList = null;
			rateListRank = null;
			outPicListRank = null;
			size = 0;
		}
		return "home";
	}
	
	public HttpServletRequest getServeletContext(){
		return ServletActionContext.getRequest();
	}
	
	/*
	 * 下面是所有属性get、set方法
	 */
	public double getRate() {
		return rate;
	}

	public void setRate(double rate) {
		this.rate = rate;
	}

	public String getInPic() {
		return inPic;
	}

	public void setInPic(String inPic) {
		this.inPic = inPic;
	}

	public String getOutPic() {
		return outPic;
	}

	public void setOutPic(String outPic) {
		this.outPic = outPic;
	}

	public AlgorithmWeight getAw() {
		return aw;
	}

	public void setAw(AlgorithmWeight aw) {
		this.aw = aw;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}
	public String[] getOutPicList() {
		return outPicList;
	}

	public void setOutPicList(String[] outPicList) {
		this.outPicList = outPicList;
	}

	public double[] getRateList() {
		return rateList;
	}

	public void setRateList(double[] rateList) {
		this.rateList = rateList;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}
	
	public String[] getOutPicListRank() {
		return outPicListRank;
	}

	public void setOutPicListRank(String[] outPicListRank) {
		this.outPicListRank = outPicListRank;
	}

	public double[] getRateListRank() {
		return rateListRank;
	}

	public void setRateListRank(double[] rateListRank) {
		this.rateListRank = rateListRank;
	}
}
