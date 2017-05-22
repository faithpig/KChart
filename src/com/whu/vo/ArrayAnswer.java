package com.whu.vo;

public class ArrayAnswer {
	
	private String[] path;
	private int size;
	private double[] rate;
	private Rank[] rank;
	
	public ArrayAnswer(int size){
		path = new String[size];
		rate = new double[size];
		this.size = size;
	}
	
	private void quicksort(Rank n[], int left, int right) {
        int dp;
        if (left < right) {
            dp = partition(n, left, right);
            quicksort(n, left, dp - 1);
            quicksort(n, dp + 1, right);
        }
    }
 
    private int partition(Rank n[], int left, int right) {
        Rank pivot = n[left];
        while (left < right) {
            while (left < right && n[right].getRate() <= pivot.getRate())
                right--;
            if (left < right)
                n[left++] = n[right];
            while (left < right && n[left].getRate() >= pivot.getRate())
                left++;
            if (left < right)
                n[right--] = n[left];
        }
        n[left] = pivot;
        return left;
    }
	
	public void setRank(){
		rank = new Rank[size];
		for(int i = 0; i<size ; i++){
			rank[i] = new Rank();
			rank[i].setTag(i+1);
			rank[i].setRate(rate[i]);
		}
		//ÅÅÐòËã·¨
		//¿ìÅÅ;
		quicksort(rank,0,size-1);
		System.out.println("½áÊø");
		
	}

	public Rank[] getRank() {
		return rank;
	}
	public int getSize(){
		return this.size;
	}
	public String[] getPath() {
		return path;
	}
	public void setPath(String[] path) {
		this.path = path;
	}
	public double[] getRate() {
		return rate;
	}
	public void setRate(double[] rate) {
		this.rate = rate;
	}
}
