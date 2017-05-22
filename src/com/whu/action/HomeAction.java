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
	
	private String inPic;//Ŀ��ͼƬ·��
	private String outPic;//��ѶԱ�ͼƬ·��
	private AlgorithmWeight aw;//�㷨Ȩ��
	private String method = "";//�û������������ĸ���ť,A��B��C��ABC�ֱ�����㷨1��2��3������㷨
	private double rate;//������ƶ�
	private String[] outPicList;//���жԱ�ͼƬ·��
	private double[] rateList;//���жԱ�ͼƬ���ƶ�
	private String[] outPicListRank;//���жԱ�ͼƬ·�������ƶȴ�С����
	private double[] rateListRank;//���жԱ�ͼƬ���ƶȰ����ƶȴ�С����
	private int size;//�Ա�ͼƬ���ܸ���


	public String welcome(){
		ArrayAnswer arrAns = null;
		if("".equals(method.trim())){
			inPic = "";
		}
		else if("init".equals(method.trim())){
			//������������ͼƬ
			HttpServletRequest req = getServeletContext();
			Answer aws = homeSer.getTarget(req.getSession().getServletContext().getRealPath("/"));
			System.out.println(req.getSession().getServletContext().getRealPath("/"));
			inPic = aws.getPath();
			HttpServletRequest request = ServletActionContext.getRequest();
			request.setAttribute("win", "<script type='text/javascript'>window.alert('����ɹ�');</script>");
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
	 * ��������������get��set����
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
