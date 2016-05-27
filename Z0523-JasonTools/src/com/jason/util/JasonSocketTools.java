package com.jason.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Jason�ļ���Socket����
 * @author Administrator
 *
 */
public class JasonSocketTools {

	private JasonSocketTools() {
	}
	
	/**
	 * ���ٽ����˿ڼ���
	 * ���߳�
	 */
	public static void easySocketServer(int port) {
		try {
			ServerSocket ss = new ServerSocket(port);
			System.out.println("��ʼ����:" + port + "�˿�...");
			
			ExecutorService es = Executors.newFixedThreadPool(10);
			
			for (;;) {
				Socket s = ss.accept();
				SockerIOtask sio = new SockerIOtask(s);
				es.execute(sio);
			}
			
			
			
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * socket IO ����
	 * @author Administrator
	 *
	 */
	private static class SockerIOtask implements Runnable{
		Socket s;
		public SockerIOtask(Socket s) {
			this.s = s;
			System.out.println(s.getRemoteSocketAddress().toString() + " ����:");
		}
		
		public void run() {
			try {
				InputStreamReader is = new InputStreamReader(s.getInputStream());
				BufferedReader  br = new BufferedReader(is);
				for(;;) {
					char[] bs = new char[1024];
					int len = is.read(bs);
					if(len == -1) {
						System.err.println(s.getRemoteSocketAddress().toString() + " �Ͽ���");
						break;
					} else {
						System.out.println(new String(bs, 0, len));
					}
				}
			}catch(Exception e) {
				e.printStackTrace();
			}
			
			try {
				s.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		}
	}
	
	/**
	 * ����get����
	 */
	public static void easySocketGet(String ip, int port) {
		String get = "GET / HTTP/1.1\r\n" + 
				"Host: " + ip + "\r\n" + 
//				"Accept: text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8\r\n" + 
//				"User-Agent: Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/50.0.2661.94 Safari/537.36\r\n" + 
				"Accept-Encoding: gzip, deflate, sdch\r\n" + 
//				"Accept-Language: zh-CN,zh;q=0.8" +
				"\r\n\r\n";
		easySocket(ip, port, get);
	}
	
	/**
	 * ����Socket����
	 * @param ip
	 * @param port
	 */
	public static void easySocket(String ip, int port, String sendInfo) {
		Socket s = null;
		try {
			s = new Socket(ip, port);
			InputStreamReader is = new InputStreamReader(s.getInputStream());
			OutputStreamWriter out = new OutputStreamWriter(s.getOutputStream());
			out.write(sendInfo);
			out.flush();
			System.out.println("Send info to :" + s.getRemoteSocketAddress().toString());
			for(;;) {
				char[] bs = new char[1024];
				int len = is.read(bs);
				if(len == -1) {
					System.err.println(s.getRemoteSocketAddress().toString() + " �Ͽ���");
					break;
				} else {
					System.out.println(new String(bs, 0, len));
				}
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		try {
			s.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
//		easySocketServer(80);
//		easySocket("192.168.1.1", 80, "asdasdasd");
		easySocketGet("baidu.com", 80);
	}

}
