package com.util;



import java.io.*;
import java.util.zip.*;


/**
 * 
 ******************************************************************* 
 *
 * ��Ŀ����    ��AT_DNSDemo
 *    
 * ������      ������ʵ����ZIPѹ��������Ϊ2���� �� ѹ����compression�����ѹ��decompression��
 *  
 * �ļ�����    ��MyZipCompressing.java
 *    
 * ��д��     ������
 *     
 * ��д����    ��2015-8-20 ����9:29:57
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

public class MyZipCompressing {
	private int k = 1; // ����ݹ��������

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
	 * �������ƣ�zip 
	 * 
	 * �������ܣ�ѹ��
	 * 
	 * ����˵����@param zipFileName
	 * ����˵����@param inputFile
	 * ����˵����@throws Exception
	 * 
	 * ���أ�void
	 * 
	 * ���ߣ����� 
	 * 
	 * ���ڣ�2015-8-20 ����9:30:25 
	 *
	 */
	public void zip(String zipFileName, File inputFile) throws Exception {
		System.out.println("ѹ����...");
		ZipOutputStream out = new ZipOutputStream(new FileOutputStream(
				zipFileName));
		BufferedOutputStream bo = new BufferedOutputStream(out);
		zip(out, inputFile, inputFile.getName(), bo);
		bo.close();
		out.close(); // ������ر�
		System.out.println("ѹ�����");
	}

	public void zip(ZipOutputStream out, File f, String base,
			BufferedOutputStream bo) throws Exception { // ��������
		if (f.isDirectory()) {
			File[] fl = f.listFiles();
			if (fl.length == 0) {
				out.putNextEntry(new ZipEntry(base + "/")); // ����zipѹ�������base
				System.out.println(base + "/");
			}
			for (int i = 0; i < fl.length; i++) {
				zip(out, fl[i], base + "/" + fl[i].getName(), bo); // �ݹ�������ļ���
			}
			System.out.println("��" + k + "�εݹ�");
			k++;
		} else {
			out.putNextEntry(new ZipEntry(base)); // ����zipѹ�������base
			System.out.println(base);
			FileInputStream in = new FileInputStream(f);
			BufferedInputStream bi = new BufferedInputStream(in);
			int b;
			while ((b = bi.read()) != -1) {
				bo.write(b); // ���ֽ���д�뵱ǰzipĿ¼
			}
			bi.close();
			in.close(); // �������ر�
		}
	}
}
