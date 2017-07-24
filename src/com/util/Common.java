package com.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;


public class Common{
	/**�Զ�����Ϊ���� ת��(short,int)����Ϊ����Ϊ2��byte����,ֻȡ��16λ. ��λ��ǰ����λ�ں�*/
	public static byte[] intTo2Byte(int num){
		byte[] buf=new byte[2];
		buf[0]=(byte)(num>>>8);
		buf[1]=(byte)(num);
		return buf;
	}
	/**�Զ�����Ϊ���� ת��(int)����Ϊ����Ϊ4��byte����,ȡ32λ ��λ��ǰ����λ�ں�*/
	public static byte[] intTo4Byte(int num){
		byte[] buf=new byte[4];
		buf[0]=(byte)(num>>>24);
		buf[1]=(byte)(num>>>16);
		buf[2]=(byte)(num>>>8);
		buf[3]=(byte)(num);
		return buf;
	}
	/**�Զ�����Ϊ���� ת��float�͸���ת��ϵ��ncת��Ϊint���ͣ��޷��ţ���ת��Ϊ����Ϊ2��byte���飬ֻת����16λ.��λ��ǰ����λ�ں�
	 * @param wave Ҫת����floatֵ
	 * @param nc  ϵ������float��ת��Ϊ����ʱ�˵�ϵ��,��Ϊ��������byte������ֺܶ಻�ɿ����أ�����ת���������ڷ���,���շ�Ӧ��ϵ��Ӧ���� 1/nc ������ȷ��ԭ��float��
	 */
	public static byte[] floatTo2Byte(float wave,float nc){
		int num=(int) (wave*nc);
		return intTo2Byte(num);
	}
	/**�Զ�����Ϊ���� ת��float�͸���ת��ϵ��ncת��Ϊint���ͣ���ת��Ϊ����Ϊ4��byte���飬ȡ32λ ��λ��ǰ����λ�ں�
	 * @param val Ҫת����floatֵ
	 * @param nc  ϵ������float��ת��Ϊ����ʱ�˵�ϵ��,��Ϊ��������byte������ֺܶ಻�ɿ����أ�����ת���������ڷ���,���շ�Ӧ��ϵ��Ӧ���� 1/nc ������ȷ��ԭ��float��
	 */
	public static byte[] floatTo4Byte(float val,float nc){
		
		int num=(int) (val*nc);
		return intTo4Byte(num);
	}
	/**����IEEE754��׼ ת��float��Ϊ����Ϊ4��byte����, ��λ��ǰ����λ�ں�
	 * @param val Ҫת����floatֵ
	 */
	public static byte[] floatIEEE754To4Byte(float val){
		
		int bits=Float.floatToIntBits(val);
		return intTo4Byte(bits);
	}
	/**�Զ�����Ϊ���� ת����ʾ16�������ֵ��ַ���Ϊbyte
	 * @param hex ����С�ڵ���2��16�����ַ���,����ַ������ȴ���2 ��ô���������Ӧ�����Ƶ����8λת�����ɵ�byte
	 * @return hex�ַ���ת�����ɵ�byte
	 * */
	public static byte hexStringToByte(String hex){
		int num=Integer.parseInt(hex, 16);
		return (byte)num;
	}
	/**�Զ�����Ϊ���� ת����ʾ16�������ֵ��ַ���Ϊbyte����
	 * @param hex 16�����ַ���
	 * @return hex�ַ���ת�����ɵ�byte���飬�䳤�ȸ����ַ������ȶ�����ע�⣡ÿ�����ַ�ת��Ϊһ��byte
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
			//����׼�����������Բ��ø���ǰһ����ô�鷳��Ҫ��0xFF���������£���������
			hexBytes[i]=(byte) (hexCharToByte(hexChars[2*i])<<4|hexCharToByte(hexChars[2*i+1]));
		}	
		return hexBytes;
	}
	
	/**�Զ�����Ϊ���� ת������Ϊ2��byte����Ϊshort���͡� ��λ��ǰ����λ�ں�*/
	public static short byte2ToShort(byte[] b){
		return (short)((b[0]<<8)|b[1]);
	}
	/**�Զ�����Ϊ���� ת������Ϊ2��byte����Ϊint���͡� ��λ��ǰ����λ�ں�*/
	public static int byte2ToInt(byte[] b){
		return (((b[0]&0xff)<<8)|(b[1]&0xff));
	}
	/**�Զ�����Ϊ���� ת������Ϊ4��byte����Ϊint���͡� ��λ��ǰ����λ�ں�*/
	public static int byte4ToInt(byte[] b){
		return ((b[0]&0xff)<<24)|((b[1]&0xff)<<16)|((b[2]&0xff)<<8)|(b[3]&0xff);
	}
	/**�Զ�����Ϊ���� ת������Ϊ4��byte����long���͡� ��λ��ǰ����λ�ں�*/
	public static long byte4ToLong(byte[] b){
		int num=byte4ToInt(b);
		return Long.parseLong(Integer.toBinaryString(num), 2);
	}
	
	/**�Զ�����Ϊ���� ת������Ϊ2��byte����Ϊ�з������ͣ�����ϵ����˻���з���float�͡� ��λ��ǰ����λ�ں�
	 * @param val Ҫת���ĳ���Ϊ2��byte����
	 * @param nc  ϵ������short����ת��Ϊfloat��ʱ�˵�ϵ����
	 * */
	public static float byte2ToFloat(byte[] val,float nc){
		int num = byte2ToShort(val);
		return num*nc;
	}
	/**�Զ�����Ϊ���� ת������Ϊ2��byte����Ϊ�޷������ͣ�����ϵ����˻���޷���float�͡� ��λ��ǰ����λ�ں�
	 * @param val Ҫת���ĳ���Ϊ2��byte����
	 * @param nc  ϵ������int����ת��Ϊfloat��ʱ�˵�ϵ����
	 * */
	public static float byte2ToUnSignedFloat(byte[] val,float nc){
		int num = byte2ToInt(val);
		return num*nc;
		
	}
	/**�Զ�����Ϊ���� ת������Ϊ4��byte����Ϊ�з������ͣ�����ϵ����˻��float�͡���λ��ǰ����λ�ں�
	 * @param val Ҫת���ĳ���Ϊ2��byte����
	 * @param nc  ϵ����������ת��Ϊfloat��ʱ�˵�ϵ����
	 * */
	public static float byte4ToFloat(byte[] val,float nc){
		int num = byte4ToInt(val);
		return num*nc;
	}
	/**�Զ�����Ϊ���� ת������Ϊ4��byte����Ϊ�޷������ͣ�����ϵ����˻��float�͡� ��λ��ǰ����λ�ں�
	 * @param val Ҫת���ĳ���Ϊ2��byte����
	 * @param nc  ϵ����������ת��Ϊfloat��ʱ�˵�ϵ����
	 * */
	public static float byte4ToUnSignedFloat(byte[] val,float nc){
		long num = byte4ToLong(val);
		return num*nc;
	}
	/**����IEEE754��׼ ת������Ϊ4��byte����Ϊfloat��, ��λ��ǰ����λ�ں�
	 * @param val Ҫת����byte����
	 */
	public static float byte4IEEE754ToFloat(byte[] val){
		int bits = byte4ToInt(val);
		return Float.intBitsToFloat(bits);
	}
	/**
	 * Ϊ�����ڵ��������һ���ֽ�
		 * 
		 * @Title:  
		 * @param:
		 * @return:byte[] 
		 * @author: Administrator	
		 * @createtime:2015-1-6����11:39:02
	 */
	public static byte[] byteArrAdd(byte[] arr,byte addPara){
		byte[] bb = new byte[arr.length + 1];
		System.arraycopy(arr, 0, bb, 0, arr.length);
		bb[bb.length-1]=addPara;
		return bb;
	}
	/** 
	 * �ֽ�ת��Ϊ���� 
	 *  
	 * @param b �ֽڣ�����4���ֽڣ� 
	 * @param index ��ʼλ�� 
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
	/**��byteת��Ϊһ��2��hex�ַ����*/
	public static String ByteToHexString(byte b){
		int num=b&0xff;
		return Integer.toHexString(num);
	}
	/**�Զ�����Ϊ���� ת��byte����Ϊ��ʾ16�������ֵ��ַ���
	 * @param byteArray byte����
	 * @param mark	�����ָ�hex�ַ��ķ��ţ�����һ��
	 * @return byte�����Ӧ��hex�ַ�����һ��byte ��ת����2��hex�ַ����
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
	/*16�����ַ�ת�ֽ�*/
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
				throw new RuntimeException("���ݷ�BCD��:"+ByteArrayToHexString(byteArray, " "));
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
	 * Unicodeת��
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
	 * ��תunicode
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
	 * �������Сʱ��
	 */
	public static Date getNearHour(Date date){
		if(date==null)return null;
		Date result=new Date(date.getTime()+30*60*1000L);
		return Common.getDate(result, "yyyy-MM-dd HH:00:00");
	}
	public static void main(String[] args) {
		
	}
}
