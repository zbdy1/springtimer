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
 * 项目名称    ：AT_DNSDemo
 *    
 * 包名称      ：des加密解密类
 *  
 * 文件名称    ：DesUtil.java
 *    
 * 编写者     ：郭靖
 *     
 * 编写日期    ：2015-8-20 上午9:38:24
 *     
 * 程序功能（类）描述 ： 
 *  
 * 程序变更日期   ：
 * 
 * 变更作者    ：
 * 
 * 变更说明    ： 
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
    
    public static String getRandomString(int length) { //length表示生成字符串的长度
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
     * 方法名称：encrypt 
     * 
     * 方法功能：加密成字符串
     * 
     * 参数说明：@param data
     * 参数说明：@param key
     * 参数说明：@return
     * 参数说明：@throws Exception
     * 
     * 返回：String
     * 
     * 作者：郭靖 
     * 
     * 日期：2015-8-20 上午9:40:25 
     *
     */
    public static String encrypt(String data, String key) throws Exception {
        byte[] bt = encrypt2(data,key);
        String strs = Base64Helper.bytesToHexString(bt);
        return strs;
    }
 
    /**
     * 
     * 方法名称：decrypt 
     * 
     * 方法功能：根据键值进行解密
     * 
     * 参数说明：@param data
     * 参数说明：@param key
     * 参数说明：@return
     * 参数说明：@throws IOException
     * 参数说明：@throws Exception
     * 
     * 返回：String
     * 
     * 作者：郭靖 
     * 
     * 日期：2015-8-20 上午9:40:12 
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
     * 方法名称：encrypt 
     * 
     * 方法功能：加密方法一
     * 
     * 参数说明：@param data
     * 参数说明：@param key
     * 参数说明：@return
     * 参数说明：@throws Exception
     * 
     * 返回：byte[]
     * 
     * 作者：郭靖 
     * 
     * 日期：2015-8-20 上午9:39:49 
     *
     */
    private static byte[] encrypt(byte[] data, byte[] key) throws Exception {
        // 生成一个可信任的随机数源
        SecureRandom sr = new SecureRandom();
 
        // 从原始密钥数据创建DESKeySpec对象
        DESKeySpec dks = new DESKeySpec(key);
 
        // 创建一个密钥工厂，然后用它把DESKeySpec转换成SecretKey对象
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(DES);
        SecretKey securekey = keyFactory.generateSecret(dks);
 
        // Cipher对象实际完成加密操作
        Cipher cipher = Cipher.getInstance(DES);
 
        // 用密钥初始化Cipher对象
        cipher.init(Cipher.ENCRYPT_MODE, securekey, sr);
 
        return cipher.doFinal(data);
    }
    /**
     * 
     * 方法名称：encrypt2 
     * 
     * 方法功能：加密方法二
     * 
     * 参数说明：@param message
     * 参数说明：@param key
     * 参数说明：@return
     * 参数说明：@throws Exception
     * 
     * 返回：byte[]
     * 
     * 作者：郭靖 
     * 
     * 日期：2015-8-20 上午9:39:26 
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
     * 方法名称：decrypt 
     * 
     * 方法功能：根据键值进行解密
     * 
     * 参数说明：@param data
     * 参数说明：@param key 加密键byte数组
     * 参数说明：@return
     * 参数说明：@throws Exception
     * 
     * 返回：byte[]
     * 
     * 作者：郭靖 
     * 
     * 日期：2015-8-20 上午9:38:51 
     *
     */
    private static byte[] decrypt(byte[] data, byte[] key) throws Exception {
        // 生成一个可信任的随机数源
        SecureRandom sr = new SecureRandom();
 
        // 从原始密钥数据创建DESKeySpec对象
        DESKeySpec dks = new DESKeySpec(key);
 
        // 创建一个密钥工厂，然后用它把DESKeySpec转换成SecretKey对象
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(DES);
        SecretKey securekey = keyFactory.generateSecret(dks);
 
        // Cipher对象实际完成解密操作
        Cipher cipher = Cipher.getInstance(DES);
 
        // 用密钥初始化Cipher对象
        cipher.init(Cipher.DECRYPT_MODE, securekey, sr);
 
        return cipher.doFinal(data);
    }
}
