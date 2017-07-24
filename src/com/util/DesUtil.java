package com.util;

import java.io.IOException;
import java.security.SecureRandom;
import java.util.Random;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;


/**
 * 
 ******************************************************************* 
 *
 * ��Ŀ����    ��AT_DNSDemo
 *    
 * ������      ��des���ܽ�����
 *  
 * �ļ�����    ��DesUtil.java
 *    
 * ��д��     ������
 *     
 * ��д����    ��2015-8-20 ����9:38:24
 *     
 * �����ܣ��ࣩ���� �� 
 *  
 * ����������   ��
 * 
 * �������    ��
 * 
 * ���˵��    �� 
 *******************************************************************
 */
public class DesUtil {
	private final static String DES = "DES";
	 
    public static void main(String[] args) throws Exception {
        String data = "123456";
        String key = "abc@1234";
        System.err.println(encrypt(data, key));
        System.err.println(decrypt(encrypt(data, key), key));
 
    }
    
    public static String getRandomString(int length) { //length��ʾ�����ַ����ĳ���
        String base = "abcdefghijklmnopqrstuvwxyzABCDEFGHIGKLMNOPQRSTUVWXYZ";   
        Random random = new Random();   
        StringBuffer sb = new StringBuffer();   
        for (int i = 0; i < length; i++) {   
            int number = random.nextInt(base.length());   
            sb.append(base.charAt(number));   
        }   
        return sb.toString();   
     }   


    /**
     * 
     * �������ƣ�encrypt 
     * 
     * �������ܣ����ܳ��ַ���
     * 
     * ����˵����@param data
     * ����˵����@param key
     * ����˵����@return
     * ����˵����@throws Exception
     * 
     * ���أ�String
     * 
     * ���ߣ����� 
     * 
     * ���ڣ�2015-8-20 ����9:40:25 
     *
     */
    public static String encrypt(String data, String key) throws Exception {
        byte[] bt = encrypt2(data,key);
        String strs = Base64Helper.bytesToHexString(bt);
        return strs;
    }
 
    /**
     * 
     * �������ƣ�decrypt 
     * 
     * �������ܣ����ݼ�ֵ���н���
     * 
     * ����˵����@param data
     * ����˵����@param key
     * ����˵����@return
     * ����˵����@throws IOException
     * ����˵����@throws Exception
     * 
     * ���أ�String
     * 
     * ���ߣ����� 
     * 
     * ���ڣ�2015-8-20 ����9:40:12 
     *
     */
    public static String decrypt(String data, String key) throws IOException,
            Exception {
        if (data == null)
            return null;
        Base64Helper decoder = new Base64Helper();
        byte[] buf = decoder.decode(data);
        byte[] bt = decrypt(buf,key.getBytes());
        return new String(bt);
    }
    
    /**
     * 
     * �������ƣ�encrypt 
     * 
     * �������ܣ����ܷ���һ
     * 
     * ����˵����@param data
     * ����˵����@param key
     * ����˵����@return
     * ����˵����@throws Exception
     * 
     * ���أ�byte[]
     * 
     * ���ߣ����� 
     * 
     * ���ڣ�2015-8-20 ����9:39:49 
     *
     */
    private static byte[] encrypt(byte[] data, byte[] key) throws Exception {
        // ����һ�������ε������Դ
        SecureRandom sr = new SecureRandom();
 
        // ��ԭʼ��Կ���ݴ���DESKeySpec����
        DESKeySpec dks = new DESKeySpec(key);
 
        // ����һ����Կ������Ȼ��������DESKeySpecת����SecretKey����
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(DES);
        SecretKey securekey = keyFactory.generateSecret(dks);
 
        // Cipher����ʵ����ɼ��ܲ���
        Cipher cipher = Cipher.getInstance(DES);
 
        // ����Կ��ʼ��Cipher����
        cipher.init(Cipher.ENCRYPT_MODE, securekey, sr);
 
        return cipher.doFinal(data);
    }
    /**
     * 
     * �������ƣ�encrypt2 
     * 
     * �������ܣ����ܷ�����
     * 
     * ����˵����@param message
     * ����˵����@param key
     * ����˵����@return
     * ����˵����@throws Exception
     * 
     * ���أ�byte[]
     * 
     * ���ߣ����� 
     * 
     * ���ڣ�2015-8-20 ����9:39:26 
     *
     */
    public static byte[] encrypt2(String message, String key) 
    		throws Exception { 
    		Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
    		DESKeySpec desKeySpec = new DESKeySpec(key.getBytes("UTF-8"));
    		SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES"); 
    		SecretKey secretKey = keyFactory.generateSecret(desKeySpec); 
    		IvParameterSpec iv = new IvParameterSpec(key.getBytes("UTF-8")); 
    		cipher.init(Cipher.ENCRYPT_MODE, secretKey, iv);
    		return cipher.doFinal(message.getBytes("GB2312")); 
    		}
    		public static byte[] convertHexString(String ss) 
    		{ 
    		byte digest[] = new byte[ss.length() / 2]; 
    		for(int i = 0; i < digest.length; i++) 
    		{ 
    		String byteString = ss.substring(2 * i, 2 * i + 2); 
    		int byteValue = Integer.parseInt(byteString, 16); 
    		digest[i] = (byte)byteValue; 
    		}
    		return digest; 
    		}
     
    /**
     * 
     * �������ƣ�decrypt 
     * 
     * �������ܣ����ݼ�ֵ���н���
     * 
     * ����˵����@param data
     * ����˵����@param key ���ܼ�byte����
     * ����˵����@return
     * ����˵����@throws Exception
     * 
     * ���أ�byte[]
     * 
     * ���ߣ����� 
     * 
     * ���ڣ�2015-8-20 ����9:38:51 
     *
     */
    private static byte[] decrypt(byte[] data, byte[] key) throws Exception {
        // ����һ�������ε������Դ
        SecureRandom sr = new SecureRandom();
 
        // ��ԭʼ��Կ���ݴ���DESKeySpec����
        DESKeySpec dks = new DESKeySpec(key);
 
        // ����һ����Կ������Ȼ��������DESKeySpecת����SecretKey����
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(DES);
        SecretKey securekey = keyFactory.generateSecret(dks);
 
        // Cipher����ʵ����ɽ��ܲ���
        Cipher cipher = Cipher.getInstance(DES);
 
        // ����Կ��ʼ��Cipher����
        cipher.init(Cipher.DECRYPT_MODE, securekey, sr);
 
        return cipher.doFinal(data);
    }
}
