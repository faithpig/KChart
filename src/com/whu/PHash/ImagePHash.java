package com.whu.PHash;

import java.awt.Graphics2D;
import java.awt.color.ColorSpace;
import java.awt.image.BufferedImage;
import java.awt.image.ColorConvertOp;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

/*
 * phash算法应用于图片相似度计算
 */
public class ImagePHash {
 
   private int size;
   private int smallerSize;
   
   
   public ImagePHash() {
	   
	   this.size = 32;
	   this.smallerSize = 8;
   }
   
   
   
   /*
    * 主要步骤
    * 获得图片的hash码，以二进制字符串表示
    * 如 001010111011100010...
    */
   public String getHash(String imgUrl) throws Exception {
	   
       BufferedImage img = ImageIO.read(new FileInputStream(new File(imgUrl)));
        
       // 1. 缩小图片尺寸,把图片变换到32*32的大小
       BufferedImage newImg = resize(img, size, size);
        
       
       // 2. 简化色彩
       img = grayscale(newImg);
       
       double[][] vals = new double[size][size];
        
       for (int i = 0; i < img.getWidth(); i++) {
           for (int j = 0; j < img.getHeight(); j++) {
               vals[i][j] = getBlue(img, i, j);
           }
       }
       
       //3. 计算DCT系数矩阵
       double[][] dctVals = applyDCT(vals);
        
       
       // 4. 缩小DCT系数矩阵，保留左上角8*8
       double total = 0.0;
        
       for (int i = 0; i < smallerSize; i++) {
           for (int j = 0; j < smallerSize; j++) {
        	   
               total += dctVals[i][j];
           }
       }
       total -= dctVals[0][0];//去掉第一个数据
       
       
       // 5. 计算均值
       double avg = total / (double) (smallerSize * smallerSize - 1);

       
       // 6. 进一步减小DCT,生成二进制字符串
       String hashStr = "";
        
       for (int i = 0; i < smallerSize; i++) {
           for (int j = 0; j < smallerSize; j++) {
        	   
        	   if(i == 0 && j == 0)
        		   continue;
               hashStr += (dctVals[i][j] >= avg ? "1" : "0");
  
           }
       }
        
       
       return hashStr;
   }
    
   
   
   /*
    * 变换图片
    */
   private BufferedImage resize(BufferedImage image, int width,int height) throws IOException{
	   
	   //创建一个BufferedImage，设定它的高、宽和类型
       BufferedImage resizedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
       
       //将原图像重画到新图像上
       Graphics2D g = resizedImage.createGraphics();
       g.drawImage(image, 0, 0, width, height, null);
       g.dispose();
       
       return resizedImage;
   }
    
   
   
   /*
    * 简化色彩,变为灰度
    */
   private BufferedImage grayscale(BufferedImage img) {
	   
	   ColorSpace cs = ColorSpace.getInstance(ColorSpace.CS_GRAY);
	   ColorConvertOp colorConvert = new ColorConvertOp(cs, null);
       colorConvert.filter(img, img);
       
       return img;
   }
   
   
   
   /*
    * 获取灰度图片的像素值
    * 灰度图片（red = green = red）
    * 任取一个分量即可
    */
   private static int getBlue(BufferedImage img, int x, int y) {
	   
       return (img.getRGB(x, y)) & 0xff;
   }
   
   
   
   /*
    * 进行离散余弦变换(二维DCT变换)
    * 返回系数矩阵
    */
   private double[][] applyDCT(double[][] f) {
	   
       int N = size;
        
       double[][] F = new double[smallerSize][smallerSize];
       double coefficients = 0.0;
       
       for (int u = 0; u < smallerSize; u++) {
         for (int v = 0; v < smallerSize; v++) {
        	 
           double sum = 0.0;
           for (int i = 0; i < N; i++) {
             for (int j = 0;j < N; j++) {
            	 
               sum += Math.cos(((i+0.5)/N)*u*Math.PI) * Math.cos(((j+0.5)/N)*v*Math.PI) * f[i][j];
               
             }
           }
           
           if(u == 0 && v == 0)
        	   coefficients = Math.sqrt(1.0 /(N*N));
           else if(u == 0 || v == 0)
        	   coefficients = Math.sqrt(2.0 /(N*N));
           else
        	   coefficients = Math.sqrt(4.0 /(N*N));
           
           sum *= coefficients;
           F[u][v] = sum;
         }
       }
       
       return F;
   }
 
   
   
   /*
    * 计算汉明距离
    */
   public int caculateHammingDistance(String hashStr1, String hashStr2) {
       
	   int distance = 0;
	   
       for (int i = 0; i < hashStr1.length(); i++) {
    	   
           if(hashStr1.charAt(i) != hashStr2.charAt(i)) {
        	   
               distance++;
           }
       }
       
       return distance;
   }
   
   
   
   /*
    * 获得两张图片的相似度
    */
   public double getSimilarity(String imageUrl1, String imageUrl2) throws Exception{
	   
	   double similarity = 0;//相似度
	   
	   String hashStr1 = getHash(imageUrl1);//第一张图片的64位二进制字符串
	   String hashStr2 = getHash(imageUrl2);//第二张图片的64位二进制字符串

	   int distance = caculateHammingDistance(hashStr1, hashStr2);//计算汉明距离
	  
	   similarity = (64 - distance) / 64.0;
	   
	   return similarity;	   
   }
   
}