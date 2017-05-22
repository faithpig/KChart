package com.whu.service;
import com.whu.DataRead.FileChoose;
import com.whu.DataRead.FileNames;
import com.whu.PHash.ImagePHash;
import com.whu.levenshtein.SimilarZzg;
import com.whu.opick.Test;
import com.whu.vo.Answer;
import com.whu.vo.ArrayAnswer;

public class HomeService {
		
	//��ȡ�����㷨����Ѵ�
	public ArrayAnswer getThreeBest(double a,double b,double c,String webpath){
		String[] temp = getAllNames(webpath);
		ArrayAnswer ans = new ArrayAnswer(temp.length-1);
		int size = temp.length;
		String[] temp2 = new String[size-1];
		double[] rate1 = new double[size-1];
		double[] rate2 = new double[size-1];
		double[] rate3 = new double[size-1];
		for(int i = 1; i<size; i++){
			temp2[i-1] = "picList/"+temp[i]+".jpg";
			try {
				rate1[i-1] = new ImagePHash().getSimilarity(webpath+"/picList/"+temp[0]+".jpg",webpath+"/picList/"+temp[i]+".jpg");
			} catch (Exception e) {
				e.printStackTrace();
			}
			rate2[i-1] = SimilarZzg.imageResult(webpath+"/picList/"+temp[0]+".jpg", webpath+"/picList/"+temp[i]+".jpg")*0.9165472; 
		}
		ans.setPath(temp2);
		double m = a+b;
		a=a/m;
		b=b/m;
		for(int i = 0 ; i < size-1; ++i)
		{
			rate3[i] = rate1[i]*a+rate2[i]*b;
		}
		ans.setRate(rate3);
		ans.setRank();
		return ans;
	}
	
	//��ȡ��֪hash�㷨��Ѵ�
	public ArrayAnswer getABest(String webpath){
		String[] temp = getAllNames(webpath);
		ArrayAnswer ans = new ArrayAnswer(temp.length-1);
		int size = temp.length;
		String[] temp2 = new String[size-1];
		double[] rate = new double[size-1];
		for(int i=1;i<size;i++){
			temp2[i-1] = "picList/"+temp[i]+".jpg"; 
			try {
				rate[i-1] =new ImagePHash().getSimilarity(webpath+"/picList/"+temp[0]+".jpg",webpath+"/picList/"+temp[i]+".jpg");
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}
		ans.setRate(rate);
		ans.setPath(temp2);
		ans.setRank();
		return ans;
	}
	
	//��ȡlevenshtein�㷨��Ѵ�
	public ArrayAnswer getBBest(String webpath){
		String[] temp = getAllNames(webpath);
		ArrayAnswer ans = new ArrayAnswer(temp.length-1);
		int size = temp.length;
		String[] temp2 = new String[size-1];
		double[] rate = new double[size-1];
		for(int i=1;i<size;i++){
			temp2[i-1] = "picList/"+temp[i]+".jpg"; 
			rate[i-1] = SimilarZzg.imageResult(webpath+"/picList/"+temp[0]+".jpg", webpath+"/picList/"+temp[i]+".jpg")*0.9165472;
		}
		ans.setRate(rate);
		ans.setPath(temp2);
		ans.setRank();
		return ans;
	}
	
	//��ȡ�㷨c��Ѵ�
	public ArrayAnswer getCBest(String webpath){
		String[] temp = getAllNames(webpath);
		ArrayAnswer ans = new ArrayAnswer(temp.length-1);
		int size = temp.length;
		String[] temp2 = new String[size-1];
		double[] rate = new double[size-1];
		for(int i=1;i<size;i++){
			temp2[i-1] = "picList/"+temp[i]+".jpg"; 
			rate[i-1] = 1/(new Test().compare(webpath+"/picList/"+temp[0], webpath+"/picList/"+temp[i]));
		}
		ans.setRate(rate);
		ans.setPath(temp2);
		ans.setRank();
		return ans;
	}
	
	//������������
	public Answer getTarget(String webpath){
		System.out.println(webpath);
		Answer ans = new Answer();
		FileChoose fc = new FileChoose(webpath);
		fc.chooseURL();
		ans.setPath("picList/"+fc.getFirstFileName()+".jpg");
		return ans;
	}
	
	//��������ļ�
	public String[] getAllNames(String webpath){
		FileNames fn = new FileNames();
		fn.init(webpath);
		return fn.getFileNames();	
	}
}
