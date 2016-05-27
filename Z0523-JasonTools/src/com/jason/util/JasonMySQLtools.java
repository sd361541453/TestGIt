package com.jason.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class JasonMySQLtools {


    public static final String url = "jdbc:mysql://127.0.0.1/test?useSSL=true&verifyServerCertificate=false";  
//	public static final String url = "jdbc:mysql://192.168.199.181/test?useSSL=true&verifyServerCertificate=false";
	public static final String name = "com.mysql.jdbc.Driver";
	public static final String user = "java";

    public static final String password = "userjava";  

	public Connection conn = null;

	public PreparedStatement pst = null;
    
	public JasonMySQLtools(String sql) {
		try {
			Class.forName(name);// ָ����������
			conn = DriverManager.getConnection(url, user, password);// ��ȡ����
			pst = conn.prepareStatement(sql);// ׼��ִ�����
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
  
    public void close() {  
        try {  
            this.conn.close();  
            this.pst.close();  
        } catch (SQLException e) {  
            e.printStackTrace();  
        }  
    }  
    
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String sql = "select * from test";// SQL���
//		String sql = "show tables";// SQL���
		JasonMySQLtools db1 = new JasonMySQLtools(sql);// ����DBHelper����

		try {
			ResultSet ret = db1.pst.executeQuery();// ִ����䣬�õ������
			while (ret.next()) {
				String uid = ret.getString(1);
				String ufname = ret.getString(2);
//				String ulname = ret.getString(3);
//				String udate = ret.getString(4);
//				System.out.println(uid + "\t" + ufname + "\t" + ulname + "\t" + udate);
				System.out.println(uid + "\t" + ufname);
//				System.out.println(uid + "\t" );
			}// ��ʾ����
			ret.close();
			db1.close();// �ر�����
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

}
