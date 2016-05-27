package com.jason.util;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.commons.httpclient.DefaultHttpMethodRetryHandler;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;

public class JasonHttpTools {

	private JasonHttpTools() {
	}
	
	/**
	 * Get����
	 * @param url
	 * @return
	 */
	public static String httpGet(String url) {
		String result = null;
		
		/* 1 ���� HttpClinet �������ò��� */
		HttpClient httpClient = new HttpClient();
		// ���� Http ���ӳ�ʱΪ5��
		httpClient.getHttpConnectionManager().getParams().setConnectionTimeout(5000);

		/* 2 ���� GetMethod �������ò��� */
		GetMethod getMethod = new GetMethod(url);
		// ���� get ����ʱΪ 5 ��
		getMethod.getParams().setParameter(HttpMethodParams.SO_TIMEOUT, 5000);
		// �����������Դ����õ���Ĭ�ϵ����Դ�����������
		getMethod.getParams().setParameter(HttpMethodParams.RETRY_HANDLER, new DefaultHttpMethodRetryHandler());

		/* 3 ִ�� HTTP GET ���� */
		try {
			int statusCode = httpClient.executeMethod(getMethod);
			/* 4 �жϷ��ʵ�״̬�� */
			if (statusCode != HttpStatus.SC_OK) {
				System.err.println("Method failed: " + getMethod.getStatusLine());
			}
			
			/*
			 5 ���� HTTP ��Ӧ���� 
			// HTTP��Ӧͷ����Ϣ������򵥴�ӡ
			Header[] headers = getMethod.getResponseHeaders();
			for (Header h : headers)
				System.out.println(h.getName() + " " + h.getValue());
			*/
			
			// ��ȡΪ InputStream������ҳ������������ʱ���Ƽ�ʹ��
			InputStream in = getMethod.getResponseBodyAsStream();
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			byte[] buf = new byte[1024];
			for(int n = 0;;) {
				n = in.read(buf);
				if(n == -1) {
					break;
				}
				out.write(buf, 0, n);
			}
			out.close();
			in.close();
			byte[] response = out.toByteArray();
			
			result = new String(response);
			
		} catch (HttpException e) {
			// �����������쳣��������Э�鲻�Ի��߷��ص�����������
			System.out.println("Please check your provided http address!");
			e.printStackTrace();
		} catch (IOException e) {
			// ���������쳣
			e.printStackTrace();
		} finally {
			/* 6 .�ͷ����� */
			getMethod.releaseConnection();
		}
		return result;
	}
	
	/**
	 * ����ͼƬ���ļ�
	 * @param url
	 * @param f
	 * @return
	 */
	public static boolean httpGetPic(String url, File f) {
		boolean result = false;

		/* 1 ���� HttpClinet �������ò��� */
		HttpClient httpClient = new HttpClient();
		// ���� Http ���ӳ�ʱΪ5��
		httpClient.getHttpConnectionManager().getParams().setConnectionTimeout(5000);

		/* 2 ���� GetMethod �������ò��� */
		GetMethod getMethod = new GetMethod(url);
		// ���� get ����ʱΪ 5 ��
		getMethod.getParams().setParameter(HttpMethodParams.SO_TIMEOUT, 5000);
		// �����������Դ����õ���Ĭ�ϵ����Դ�����������
		getMethod.getParams().setParameter(HttpMethodParams.RETRY_HANDLER, new DefaultHttpMethodRetryHandler());

		/* 3 ִ�� HTTP GET ���� */
		try {
			int statusCode = httpClient.executeMethod(getMethod);
			/* 4 �жϷ��ʵ�״̬�� */
			if (statusCode != HttpStatus.SC_OK) {
				System.err.println("Method failed: " + getMethod.getStatusLine());
			}
			
			InputStream in = getMethod.getResponseBodyAsStream();
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			byte[] buf = new byte[1024];
			for(int n = 0;;) {
				n = in.read(buf);
				if(n == -1) {
					break;
				}
				out.write(buf, 0, n);
			}
			out.close();
			in.close();
			byte[] response = out.toByteArray();
			
			//save
			FileOutputStream fos = new FileOutputStream(f);
			fos.write(response);
			fos.close();

			result = true;
			
		} catch (HttpException e) {
			// �����������쳣��������Э�鲻�Ի��߷��ص�����������
			System.out.println("Please check your provided http address!");
			e.printStackTrace();
			result = false;
		} catch (IOException e) {
			// ���������쳣
			e.printStackTrace();
			result = false;
		} finally {
			/* 6 .�ͷ����� */
			getMethod.releaseConnection();
		}
		return result;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String s = httpGet("http://www.baidu.com");
//		String s = httpGet("https://www.ibm.com/developerworks/cn/opensource/os-cn-crawler/images/image001.jpg");
		System.out.println(s);
//		System.out.println(httpGetPic("http://attach.bbs.miui.com/forum/201502/08/101239c6006ffxvv0d206i.jpg"
//				,new File("D://test/11/test.jpg")));
	}

}
