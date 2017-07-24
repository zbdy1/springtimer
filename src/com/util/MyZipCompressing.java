package com.util;



import java.io.*;
import java.util.zip.*;


/**
 * 
 ******************************************************************* 
 *
 * 项目名称    ：AT_DNSDemo
 *    
 * 包名称      ：程序实现了ZIP压缩。共分为2部分 ： 压缩（compression）与解压（decompression）
 *  
 * 文件名称    ：MyZipCompressing.java
 *    
 * 编写者     ：郭靖
 *     
 * 编写日期    ：2015-8-20 上午9:29:57
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

public class MyZipCompressing {
	private int k = 1; // 定义递归次数变量

	public MyZipCompressing() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		MyZipCompressing book = new MyZipCompressing();
		try {
			book.zip("C:\\Users\\Gaowen\\Desktop\\ZipTestCompressing.zip",
					new File("C:\\Users\\Gaowen\\Documents\\Tencent Files"));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * 
	 * 方法名称：zip 
	 * 
	 * 方法功能：压缩
	 * 
	 * 参数说明：@param zipFileName
	 * 参数说明：@param inputFile
	 * 参数说明：@throws Exception
	 * 
	 * 返回：void
	 * 
	 * 作者：郭靖 
	 * 
	 * 日期：2015-8-20 上午9:30:25 
	 *
	 */
	public void zip(String zipFileName, File inputFile) throws Exception {
		System.out.println("压缩中...");
		ZipOutputStream out = new ZipOutputStream(new FileOutputStream(
				zipFileName));
		BufferedOutputStream bo = new BufferedOutputStream(out);
		zip(out, inputFile, inputFile.getName(), bo);
		bo.close();
		out.close(); // 输出流关闭
		System.out.println("压缩完成");
	}

	public void zip(ZipOutputStream out, File f, String base,
			BufferedOutputStream bo) throws Exception { // 方法重载
		if (f.isDirectory()) {
			File[] fl = f.listFiles();
			if (fl.length == 0) {
				out.putNextEntry(new ZipEntry(base + "/")); // 创建zip压缩进入点base
				System.out.println(base + "/");
			}
			for (int i = 0; i < fl.length; i++) {
				zip(out, fl[i], base + "/" + fl[i].getName(), bo); // 递归遍历子文件夹
			}
			System.out.println("第" + k + "次递归");
			k++;
		} else {
			out.putNextEntry(new ZipEntry(base)); // 创建zip压缩进入点base
			System.out.println(base);
			FileInputStream in = new FileInputStream(f);
			BufferedInputStream bi = new BufferedInputStream(in);
			int b;
			while ((b = bi.read()) != -1) {
				bo.write(b); // 将字节流写入当前zip目录
			}
			bi.close();
			in.close(); // 输入流关闭
		}
	}
}
