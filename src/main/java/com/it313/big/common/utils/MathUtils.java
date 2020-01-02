package com.it313.big.common.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;

	public class MathUtils {

		//金额四舍五入
		public static double round(double d){
			BigDecimal bd=new BigDecimal(d);//建议使用String参数
			BigDecimal bd_half_even = bd.setScale(2,RoundingMode.HALF_EVEN);
			//BigDecimal bd_half_up = bd.setScale(2,RoundingMode.HALF_UP);
			return bd_half_even.doubleValue();
		} 
		
		 /**
	     * 加法
	     *
	     * @param var1
	     * @param var2
	     * @return
	     */
	    public static double add(double var1, double var2) {
	        BigDecimal b1 = new BigDecimal(Double.toString(var1));
	        BigDecimal b2 = new BigDecimal(Double.toString(var2));
	        return b1.add(b2).doubleValue();
	    }

	    /**
	     * 减法
	     *
	     * @param var1
	     * @param var2
	     * @return
	     */
	    public static double sub(double var1, double var2) {
	        BigDecimal b1 = new BigDecimal(Double.toString(var1));
	        BigDecimal b2 = new BigDecimal(Double.toString(var2));
	        return b1.subtract(b2).doubleValue();
	    }
	    /**
	     * 减法
	     *
	     * @param var1
	     * @param var2
	     * @return
	     */
	    
	    public static double sub(String var1, String var2) {
	    	BigDecimal b1 = new BigDecimal(var1);
	    	BigDecimal b2 = new BigDecimal(var2);
	    	return b1.subtract(b2).doubleValue();
	    }

	    /**
	     * 乘法
	     *
	     * @param var1
	     * @param var2
	     * @return
	     */
	    public static double mul(double var1, double var2) {
	        BigDecimal b1 = new BigDecimal(Double.toString(var1));
	        BigDecimal b2 = new BigDecimal(Double.toString(var2));
	        return b1.multiply(b2).doubleValue();
	    }
	    
	    /**
	     * 乘法
	     *
	     * @param var1
	     * @param var2
	     * @return
	     */
	    public static double mul(String var1, String var2) {
	    	BigDecimal b1 = new BigDecimal(var1);
	    	BigDecimal b2 = new BigDecimal(var2);
	    	return b1.multiply(b2).doubleValue();
	    }

	    /**
	     * 除法
	     * @param v1
	     * @param v2
	     * @param scale 精度，到小数点后几位
	     * @return
	     */
	    public static double div(double v1, double v2, int scale) {
	        if (scale < 0) {
	            throw new IllegalArgumentException("The scale must be a positive integer or ");
	        }
	        BigDecimal b1 = new BigDecimal(Double.toString(v1));
	        BigDecimal b2 = new BigDecimal(Double.toString(v2));
	        return b1.divide(b2, scale, BigDecimal.ROUND_HALF_EVEN).doubleValue();
	    }
	    
	    /**
	     * 除法、向上取整
	     * @param v1
	     * @param v2
	     * @param scale 精度，到小数点后几位
	     * @return
	     */
	    public static double divUp(double v1, double v2, int scale) {
	    	if (scale < 0) {
	    		throw new IllegalArgumentException("The scale must be a positive integer or ");
	    	}
	    	BigDecimal b1 = new BigDecimal(Double.toString(v1));
	    	BigDecimal b2 = new BigDecimal(Double.toString(v2));
	    	return b1.divide(b2, scale, BigDecimal.ROUND_UP).doubleValue();
	    }

		
		/**
		 * 截取我用的零
		 * @param d
		 * @return
		 */
		public static String inteceptZero(double d){
			String s = d+"";
			  if(s.indexOf(".") > 0){
			     //正则表达
			           s = s.replaceAll("0+?$", "");//去掉后面无用的零
			           s = s.replaceAll("[.]$", "");//如小数点后面全是零则去掉小数点
			     }
			return s;  
		}
	}
