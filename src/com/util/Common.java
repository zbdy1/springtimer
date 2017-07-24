package com.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;


public class Common{
	/**以二进制为基础 转换(short,int)整型为长度为2的byte数组,只取低16位. 高位在前，低位在后*/
	public static byte[] intTo2Byte(int num){
		byte[] buf=new byte[2];
		buf[0]=(byte)(num>>>8);
		buf[1]=(byte)(num);
		return buf;
	}
	/**以二进制为基础 转换(int)整型为长度为4的byte数组,取32位 高位在前，低位在后*/
	public static byte[] intTo4Byte(int num){
		byte[] buf=new byte[4];
		buf[0]=(byte)(num>>>24);
		buf[1]=(byte)(num>>>16);
		buf[2]=(byte)(num>>>8);
		buf[3]=(byte)(num);
		return buf;
	}
	/**以二进制为基础 转换float型根据转换系数nc转化为int整型，无符号，再转化为长度为2的byte数组，只转换低16位.高位在前，低位在后
	 * @param wave 要转化的float值
	 * @param nc  系数（及float型转化为整型时乘的系数,因为浮点数用byte传输出现很多不可控因素，所以转换成整型在发送,接收方应的系数应该是 1/nc 方能正确还原该float）
	 */
	public static byte[] floatTo2Byte(float wave,float nc){
		int num=(int) (wave*nc);
		return intTo2Byte(num);
	}
	/**以二进制为基础 转换float型根据转换系数nc转化为int整型，再转化为长度为4的byte数组，取32位 高位在前，低位在后
	 * @param val 要转化的float值
	 * @param nc  系数（及float型转化为整型时乘的系数,因为浮点数用byte传输出现很多不可控因素，所以转换成整型在发送,接收方应的系数应该是 1/nc 方能正确还原该float）
	 */
	public static byte[] floatTo4Byte(float val,float nc){
		
		int num=(int) (val*nc);
		return intTo4Byte(num);
	}
	/**根据IEEE754标准 转换float型为长度为4的byte数组, 高位在前，低位在后
	 * @param val 要转化的float值
	 */
	public static byte[] floatIEEE754To4Byte(float val){
		
		int bits=Float.floatToIntBits(val);
		return intTo4Byte(bits);
	}
	/**以二进制为基础 转换表示16进制数字的字符串为byte
	 * @param hex 长度小于等于2的16进制字符串,如果字符串长度大于2 那么将返回其对应二进制的最低8位转化而成的byte
	 * @return hex字符串转换而成的byte
	 * */
	public static byte hexStringToByte(String hex){
		int num=Integer.parseInt(hex, 16);
		return (byte)num;
	}
	/**以二进制为基础 转换表示16进制数字的字符串为byte数组
	 * @param hex 16进制字符串
	 * @return hex字符串转换而成的byte数组，其长度根据字符串长度而定，注意！每两个字符转换为一个byte
	 * */
	public static byte[] hexStringToByteArray(String hex){
		if(hex.equals(null)||hex.equals(""))
		{
			return null;
		}
		char[] hexChars=hex.toCharArray();
		int len=hexChars.length/2;
		byte[] hexBytes=new byte[len];
		for(int i=0;i<len;i++){
			//这里准是整数，所以不用跟以前一样那么麻烦还要与0xFF与运算以下，消除符号
			hexBytes[i]=(byte) (hexCharToByte(hexChars[2*i])<<4|hexCharToByte(hexChars[2*i+1]));
		}	
		return hexBytes;
	}
	
	/**以二进制为基础 转换长度为2的byte数组为short整型。 高位在前，低位在后*/
	public static short byte2ToShort(byte[] b){
		return (short)((b[0]<<8)|b[1]);
	}
	/**以二进制为基础 转换长度为2的byte数组为int整型。 高位在前，低位在后*/
	public static int byte2ToInt(byte[] b){
		return (((b[0]&0xff)<<8)|(b[1]&0xff));
	}
	/**以二进制为基础 转换长度为4的byte数组为int整型。 高位在前，低位在后*/
	public static int byte4ToInt(byte[] b){
		return ((b[0]&0xff)<<24)|((b[1]&0xff)<<16)|((b[2]&0xff)<<8)|(b[3]&0xff);
	}
	/**以二进制为基础 转换长度为4的byte数组long整型。 高位在前，低位在后*/
	public static long byte4ToLong(byte[] b){
		int num=byte4ToInt(b);
		return Long.parseLong(Integer.toBinaryString(num), 2);
	}
	
	/**以二进制为基础 转化长度为2的byte数组为有符号整型，再与系数相乘获得有符号float型。 高位在前，低位在后
	 * @param val 要转化的长度为2的byte数组
	 * @param nc  系数（及short整型转化为float型时乘的系数）
	 * */
	public static float byte2ToFloat(byte[] val,float nc){
		int num = byte2ToShort(val);
		return num*nc;
	}
	/**以二进制为基础 转化长度为2的byte数组为无符号整型，再与系数相乘获得无符号float型。 高位在前，低位在后
	 * @param val 要转化的长度为2的byte数组
	 * @param nc  系数（及int整型转化为float型时乘的系数）
	 * */
	public static float byte2ToUnSignedFloat(byte[] val,float nc){
		int num = byte2ToInt(val);
		return num*nc;
		
	}
	/**以二进制为基础 转化长度为4的byte数组为有符号整型，再与系数相乘获得float型。高位在前，低位在后
	 * @param val 要转化的长度为2的byte数组
	 * @param nc  系数（及整型转化为float型时乘的系数）
	 * */
	public static float byte4ToFloat(byte[] val,float nc){
		int num = byte4ToInt(val);
		return num*nc;
	}
	/**以二进制为基础 转化长度为4的byte数组为无符号整型，再与系数相乘获得float型。 高位在前，低位在后
	 * @param val 要转化的长度为2的byte数组
	 * @param nc  系数（及整型转化为float型时乘的系数）
	 * */
	public static float byte4ToUnSignedFloat(byte[] val,float nc){
		long num = byte4ToLong(val);
		return num*nc;
	}
	/**根据IEEE754标准 转换长度为4的byte数组为float型, 高位在前，低位在后
	 * @param val 要转化的byte数组
	 */
	public static float byte4IEEE754ToFloat(byte[] val){
		int bits = byte4ToInt(val);
		return Float.intBitsToFloat(bits);
	}
	/**
	 * 为己存在的数组添加一个字节
		 * 
		 * @Title:  
		 * @param:
		 * @return:byte[] 
		 * @author: Administrator	
		 * @createtime:2015-1-6上午11:39:02
	 */
	public static byte[] byteArrAdd(byte[] arr,byte addPara){
		byte[] bb = new byte[arr.length + 1];
		System.arraycopy(arr, 0, bb, 0, arr.length);
		bb[bb.length-1]=addPara;
		return bb;
	}
	/** 
	 * 字节转换为浮点 
	 *  
	 * @param b 字节（至少4个字节） 
	 * @param index 开始位置 
	 * @return 
	 */  
	public static float byte4ToFloat(byte[] b, int index){
	    int l;                                             
	    l = b[index + 0];                                  
	    l &= 0xff;                                         
	    l |= ((long) b[index + 1] << 8);                   
	    l &= 0xffff;                                       
	    l |= ((long) b[index + 2] << 16);                  
	    l &= 0xffffff;                                     
	    l |= ((long) b[index + 3] << 24);                  
	    return Float.intBitsToFloat(l);                    
	}
	/**将byte转化为一个2个hex字符输出*/
	public static String ByteToHexString(byte b){
		int num=b&0xff;
		return Integer.toHexString(num);
	}
	/**以二进制为基础 转换byte数组为表示16进制数字的字符串
	 * @param byteArray byte数组
	 * @param mark	用来分隔hex字符的符号，二个一组
	 * @return byte数组对应的hex字符串，一个byte 将转换成2个hex字符输出
	 * */
	public static String ByteArrayToHexString(byte[] byteArray,String mark){
		 String s="";
		 for(int i=0,len=byteArray.length;i<len;i++)
		 {
			 int num=byteArray[i]&0xFF;
			 String tmp=Integer.toHexString(num);
			 if(tmp.length()==1){
				 s+="0";
			 }
			 s+=tmp.toUpperCase();
			 s+=mark;
		 }
		 return s;
	}
	/*16进制字符转字节*/
	private static byte hexCharToByte(char c) {
		char ch=Character.toUpperCase(c);
		String s="0123456789ABCDEF";
		return (byte)s.indexOf(ch);
	}
	public static String getNow(){
		String pattern="yyyy-MM-dd HH:mm:ss";
		return getNow(pattern);
	}
	public static String getDateString(Date d){
		String pattern="yyyy-MM-dd HH:mm:ss";
		return formatDate(pattern,d);
	}
	public static int byte4BCDToInt(byte[] byteArray){
		int sum=0;
		for(byte b:byteArray){
			if(b/16>9||b%16>9){
				throw new RuntimeException("数据非BCD码:"+ByteArrayToHexString(byteArray, " "));
			}
			sum=((sum*10)+(b/16))*10+b%16;
		}
		return sum;
	}
	public static String formatDate(String pattern,Date d){
		SimpleDateFormat sdf=new SimpleDateFormat(pattern);
		return sdf.format(d);
	}
	public static Date getNowDate(String pattern) throws ParseException{
		SimpleDateFormat sdf=new SimpleDateFormat(pattern);
		return sdf.parse(getNow(pattern));
	}
	public static String getNow(String pattern){
		SimpleDateFormat sdf=new SimpleDateFormat(pattern);
		return sdf.format(new Date());
	}
	public static String getDateString(Date d,String param){
		SimpleDateFormat sdf=new SimpleDateFormat(param);
		if(d==null){
			return sdf.format(new Date());
		}else{
			return sdf.format(d);
		}
	}
	public static Date getDate(Date date,String pattern){
		String dateString=Common.getDateString(date, pattern);
		return getDate(dateString,pattern);
	}
	public static Date getDate(String date,String pattern){
		SimpleDateFormat sdf=new SimpleDateFormat(pattern);
		try {
			return sdf.parse(date);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * Unicode转串
	 * @param str
	 * @return
	 */
	public static String unicode2Str(String str) {

		StringBuffer sb = new StringBuffer();
		String[] arr = str.split("\\\\u");
		int len = arr.length;
		sb.append(arr[0]);
		for (int i = 1; i < len; i++) {
			String tmp = arr[i];
			char c = (char) Integer.parseInt(tmp.substring(0, 4), 16);
			sb.append(c);
			sb.append(tmp.substring(4));
		}
		return sb.toString();
	}
	/**
	 * 串转unicode
	 * @param str
	 * @return
	 */
	public static String str2Unicode(String str) {
		StringBuffer sb = new StringBuffer();
		char[] charArr = str.toCharArray();
		for (char ch : charArr) {
			if (ch > 128) {
				sb.append("\\u" + Integer.toHexString(ch));
			} else {
				sb.append(ch);
			}
		}
		return sb.toString();
	}
	/**
	 * 距离最近小时数
	 */
	public static Date getNearHour(Date date){
		if(date==null)return null;
		Date result=new Date(date.getTime()+30*60*1000L);
		return Common.getDate(result, "yyyy-MM-dd HH:00:00");
	}
	public static void main(String[] args) {
		
	}
}
